package com.Inovatech.Java.Inovatech.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Pedido_has_Produto")
public class PedidoHasProduto {

    @Embeddable
    public static class PedidoHasProdutoId implements Serializable {
        @Column(name = "Pedido_idPedido")
        private Integer pedidoId;

        @Column(name = "Pedido_Cliente_idCliente")
        private Integer clienteId;

        @Column(name = "Produto_idProduto")
        private Integer produtoId;

        // Corrigir setters para atribuição dos valores
        public void setProdutoId(Integer produtoId) {
            this.produtoId = produtoId;
        }

        public void setClienteId(Integer clienteId) {
            this.clienteId = clienteId;
        }

        public void setPedidoId(Integer pedidoId) {
            this.pedidoId = pedidoId;
        }

        // Getters, hashCode e equals
        public Integer getPedidoId() {
            return pedidoId;
        }

        public Integer getClienteId() {
            return clienteId;
        }

        public Integer getProdutoId() {
            return produtoId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PedidoHasProdutoId that = (PedidoHasProdutoId) o;
            return Objects.equals(pedidoId, that.pedidoId) &&
                    Objects.equals(clienteId, that.clienteId) &&
                    Objects.equals(produtoId, that.produtoId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pedidoId, clienteId, produtoId);
        }
    }

    @EmbeddedId
    private PedidoHasProdutoId id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Pedido_idPedido", referencedColumnName = "idPedido", insertable = false, updatable = false),
            @JoinColumn(name = "Pedido_Cliente_idCliente", referencedColumnName = "Cliente_idCliente", insertable = false, updatable = false)
    })
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "Produto_idProduto", insertable = false, updatable = false)
    private Produto produto;

    @Column(name = "quantidade")
    private Integer quantidade;  // Novo campo para armazenar a quantidade de cada item

    // Getters e Setters
    public PedidoHasProdutoId getId() {
        return id;
    }

    public void setId(PedidoHasProdutoId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
