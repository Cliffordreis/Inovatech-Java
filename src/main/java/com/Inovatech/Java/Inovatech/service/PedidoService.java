package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.dto.CarrinhoItem;
import com.Inovatech.Java.Inovatech.model.*;
import com.Inovatech.Java.Inovatech.repositories.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private StatusCacheRepository statusCacheRepository;

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoHasProdutoRepository pedidoHasProdutoRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate; // Para enviar mensagens ao RabbitMQ

    private String statusQueue = "p.pagClif";
    private String PedidoQueue = "EmailPedidoCli";

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository, PedidoHasProdutoRepository pedidoHasProdutoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoHasProdutoRepository = pedidoHasProdutoRepository;
    }

    public Pedido finalizarPedido(List<CarrinhoItem> carrinho, Integer clienteId) {
        // Busca o cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Verificar o estoque antes de criar o pedido
        for (CarrinhoItem item : carrinho) {
            Produto produto = produtoRepository.findById(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            // Verifica se há estoque suficiente antes de criar o pedido
            if (produto.getProdutoQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getProdutoDescricao());
            }
        }

        // Criação do pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPedidoDataHora(LocalDateTime.now());
        pedido.setPedidoValor(calcularTotalCarrinho(carrinho));

        // Salva o pedido no banco (garante que o idPedido seja gerado)
        pedido = pedidoRepository.save(pedido);

        // Associa os produtos ao pedido e atualiza o estoque
        for (CarrinhoItem item : carrinho) {
            Produto produto = produtoRepository.findById(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            // Criação da associação do produto com o pedido
            PedidoHasProduto pedidoHasProduto = new PedidoHasProduto();

            // Definindo a chave composta
            PedidoHasProduto.PedidoHasProdutoId id = new PedidoHasProduto.PedidoHasProdutoId();
            id.setPedidoId(pedido.getIdPedido());  // Garantir que o idPedido do pedido seja correto
            id.setClienteId(cliente.getIdCliente());  // Garantir que o clienteId esteja correto
            id.setProdutoId(produto.getIdProduto());  // Garantir que o produtoId esteja correto

            // Definindo a chave composta no objeto PedidoHasProduto
            pedidoHasProduto.setId(id);

            // Definindo o pedido, produto e quantidade
            pedidoHasProduto.setPedido(pedido);
            pedidoHasProduto.setProduto(produto);
            pedidoHasProduto.setQuantidade(item.getQuantidade());

            // Salvar a associação
            pedidoHasProdutoRepository.save(pedidoHasProduto);

            // Atualizando a quantidade do estoque
            produto.setProdutoQuantidade(produto.getProdutoQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);  // Atualiza o produto com a nova quantidade

        }

        //atualizando cache do status
        StatusCache statusCache = new StatusCache();
        statusCache.setPedidoId(pedido);
        statusCache.setStatusDescricao("Pedido criado!");
        statusCache.setUltimaAtualizacao(LocalDateTime.now());
        statusCacheRepository.save(statusCache);

        enviarStatusParaMicroservice(pedido.getIdPedido(), "Pedido Criado");
        enviarPedidoParaFila(pedido, carrinho, cliente.getEmailCliente(), cliente.getNomeCliente(), cliente.getSobrenomeCliente());
        return pedido;
    }

    private void enviarStatusParaMicroservice(Integer pedidoId, String statusDescricao) {
        // Cria uma mensagem de status
        Map<String, Object> mensagem = new HashMap<>();
        mensagem.put("pedidoId", pedidoId);
        mensagem.put("statusDescricao", statusDescricao);
        mensagem.put("ultimaAtualizacao", LocalDateTime.now());

        // Publica a mensagem no RabbitMQ
        rabbitTemplate.convertAndSend(statusQueue, mensagem);
    }

    private void enviarPedidoParaFila(Pedido pedido, List<CarrinhoItem> carrinho, String emailCliente,String nomeCliente, String sobrenomeCLiente) {
        // Criando a estrutura da mensagem
        Map<String, Object> mensagem = new HashMap<>();
        mensagem.put("nome", nomeCliente);
        mensagem.put("sobrenome", sobrenomeCLiente);
        mensagem.put("email", emailCliente);
        mensagem.put("idPedido", pedido.getIdPedido());
        mensagem.put("valorTotal", pedido.getPedidoValor());
        mensagem.put("clienteId", pedido.getCliente().getIdCliente());
        mensagem.put("dataHora", pedido.getPedidoDataHora());

        // Criando a lista de produtos
        List<Map<String, Object>> produtos = carrinho.stream().map(item -> {
            Map<String, Object> produtoInfo = new HashMap<>();
            produtoInfo.put("idProduto", item.getProdutoId());
            produtoInfo.put("nome", item.getNome());
            produtoInfo.put("quantidade", item.getQuantidade());
            produtoInfo.put("precoUnitario", item.getPrecoUnitario());
            return produtoInfo;
        }).toList();

        mensagem.put("produtos", produtos);

        // Publicando a mensagem no RabbitMQ
        rabbitTemplate.convertAndSend(PedidoQueue, mensagem);
    }







    private BigDecimal calcularTotalCarrinho(List<CarrinhoItem> carrinho) {
        return carrinho.stream()
                .map(CarrinhoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Método para buscar pedidos de um cliente
    public List<Pedido> findByCliente_idCliente(Integer idCliente) {
        return pedidoRepository.findByCliente_idCliente(idCliente);
    }

    // Método para buscar PedidoHasProduto de um cliente
    public List<PedidoHasProduto> findByPedido_Cliente_idCliente(Integer idCliente) {
        return pedidoHasProdutoRepository.findByPedido_Cliente_idCliente(idCliente);
    }
    public List<Pedido> getPedidosComProdutos(Integer clienteId) {
        return pedidoRepository.findPedidosComProdutos(clienteId);
    }

}
