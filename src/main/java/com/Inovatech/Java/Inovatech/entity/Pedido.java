package com.Inovatech.Java.Inovatech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pedido")
@Getter
@Setter
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    private String pedidoDescricao;
    private BigDecimal pedidoValor;
    private LocalDateTime pedidoDataHora;
    private Integer pedidoQuantidade;

    @ManyToOne
    @JoinColumn(name = "Cliente_idCliente", nullable = false)
    private Cliente cliente;

    public Pedido() {

    }


    // Getters e setters

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getPedidoDescricao() {
        return pedidoDescricao;
    }

    public void setPedidoDescricao(String pedidoDescricao) {
        this.pedidoDescricao = pedidoDescricao;
    }

    public BigDecimal getPedidoValor() {
        return pedidoValor;
    }

    public void setPedidoValor(BigDecimal pedidoValor) {
        this.pedidoValor = pedidoValor;
    }

    public Integer getPedidoQuantidade() {
        return pedidoQuantidade;
    }

    public void setPedidoQuantidade(Integer pedidoQuantidade) {
        this.pedidoQuantidade = pedidoQuantidade;
    }

    public LocalDateTime getPedidoDataHora() {
        return pedidoDataHora;
    }

    public void setPedidoDataHora(LocalDateTime pedidoDataHora) {
        this.pedidoDataHora = pedidoDataHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}