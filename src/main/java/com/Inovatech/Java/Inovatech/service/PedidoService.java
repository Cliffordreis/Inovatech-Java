package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import com.Inovatech.Java.Inovatech.entity.Pedido;
import com.Inovatech.Java.Inovatech.entity.Produto;
import com.Inovatech.Java.Inovatech.exception.EstoqueInsuficienteException;
import com.Inovatech.Java.Inovatech.repositories.ClienteRepository;
import com.Inovatech.Java.Inovatech.repositories.PedidoRepository;
import com.Inovatech.Java.Inovatech.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Pedido criarPedido(Integer clienteId, Integer produtoId, int quantidade) throws EstoqueInsuficienteException {
        // Busca o cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));

        // Busca o produto
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + produtoId));

        // Verifica se a quantidade solicitada está disponível
        if (produto.getProdutoQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getProdutoDescricao());
        }

        // Calcula o valor total
        BigDecimal valorTotal = BigDecimal.valueOf(produto.getProdutoValor()).multiply(BigDecimal.valueOf(quantidade));

        // Atualiza o estoque do produto
        produto.setProdutoQuantidade(produto.getProdutoQuantidade() - quantidade);
        produtoRepository.save(produto); // Salva a atualização no banco de dados

        // Cria o pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setPedidoDescricao(produto.getProdutoDescricao());
        pedido.setPedidoValor(valorTotal);
        pedido.setPedidoDataHora(LocalDateTime.now());
        pedido.setPedidoQuantidade(quantidade);

        // Salva o pedido
        return pedidoRepository.save(pedido);
    }

}
