package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.entity.CarrinhoItem;
import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.entity.Pedido;
import com.Inovatech.Java.Inovatech.entity.PedidoHasProduto;
import com.Inovatech.Java.Inovatech.entity.Produto;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.repositories.PedidoRepository;
import com.Inovatech.Java.Inovatech.repositories.ProdutoRepository;
import com.Inovatech.Java.Inovatech.repositories.PedidoHasProdutoRepository; // Importando o repositório correto
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoHasProdutoRepository pedidoHasProdutoRepository;

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

        // Criação do pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPedidoDataHora(LocalDateTime.now());
        pedido.setPedidoValor(calcularTotalCarrinho(carrinho));

        // Salva o pedido no banco
        pedido = pedidoRepository.save(pedido);

        // Associa os produtos ao pedido e atualiza o estoque
        for (CarrinhoItem item : carrinho) {
            Produto produto = produtoRepository.findById(item.getProdutoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

            // Verifica se há estoque suficiente
            if (produto.getProdutoQuantidade() < item.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getProdutoDescricao());
            }

            // Cria a associação do produto com o pedido
            PedidoHasProduto pedidoHasProduto = new PedidoHasProduto();
            PedidoHasProduto.PedidoHasProdutoId id = new PedidoHasProduto.PedidoHasProdutoId();
            id.setPedidoId(pedido.getIdPedido());
            id.setClienteId(cliente.getIdCliente());  // Garantir que o clienteId seja passado
            id.setProdutoId(produto.getIdProduto());

            pedidoHasProduto.setId(id); // Definindo a chave composta
            pedidoHasProduto.setPedido(pedido);
            pedidoHasProduto.setProduto(produto);
            pedidoHasProduto.setQuantidade(item.getQuantidade()); // Aqui também você pode setar a quantidade do produto

            // Atualiza o estoque
            produto.setProdutoQuantidade(produto.getProdutoQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);

            // Salva a associação
            pedidoHasProdutoRepository.save(pedidoHasProduto);
        }

        return pedido;
    }






    private BigDecimal calcularTotalCarrinho(List<CarrinhoItem> carrinho) {
        return carrinho.stream()
                .map(CarrinhoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
