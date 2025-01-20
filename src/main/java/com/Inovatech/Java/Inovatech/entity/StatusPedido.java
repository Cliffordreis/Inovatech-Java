package com.Inovatech.Java.Inovatech.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StatusPedido")
public class StatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatusPedido;

    @Column(name = "StatusPedidoDescricao")
    private LocalDateTime statusPedidoDescricao;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Pedido_idPedido", referencedColumnName = "idPedido"),
            @JoinColumn(name = "Pedido_Cliente_idCliente", referencedColumnName = "Cliente_idCliente")
    })
    private Pedido pedido;

    // Getters e Setters
    public Integer getIdStatusPedido() {
        return idStatusPedido;
    }

    public void setIdStatusPedido(Integer idStatusPedido) {
        this.idStatusPedido = idStatusPedido;
    }

    public LocalDateTime getStatusPedidoDescricao() {
        return statusPedidoDescricao;
    }

    public void setStatusPedidoDescricao(LocalDateTime statusPedidoDescricao) {
        this.statusPedidoDescricao = statusPedidoDescricao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
