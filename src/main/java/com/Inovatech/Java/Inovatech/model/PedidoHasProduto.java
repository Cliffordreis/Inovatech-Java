package com.Inovatech.Java.Inovatech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Pedido_has_Produto")
public class PedidoHasProduto {

    // Classe interna para chave composta
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class PedidoHasProdutoId implements Serializable {

        @Column(name = "Pedido_idPedido")
        private Integer pedidoId;

        @Column(name = "Pedido_Cliente_idCliente")
        private Integer clienteId;

        @Column(name = "Produto_idProduto")
        private Integer produtoId;
    }

    // Usando a chave composta
    @EmbeddedId
    private PedidoHasProdutoId id;

    // Relacionamento com Pedido
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Pedido_idPedido", referencedColumnName = "idPedido", insertable = false, updatable = false),
            @JoinColumn(name = "Pedido_Cliente_idCliente", referencedColumnName = "Cliente_idCliente", insertable = false, updatable = false)
    })
    private Pedido pedido;

    // Relacionamento com Produto
    @ManyToOne
    @JoinColumn(name = "Produto_idProduto", insertable = false, updatable = false)
    private Produto produto;

    // Atributo de quantidade
    @Column(name = "quantidade")
    private Integer quantidade;
}
