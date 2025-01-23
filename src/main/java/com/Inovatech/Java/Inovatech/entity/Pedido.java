package com.Inovatech.Java.Inovatech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Pedido")
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "pedido_seq", initialValue = 1000, allocationSize = 1)
    private Integer idPedido;

    private BigDecimal pedidoValor;
    private LocalDateTime pedidoDataHora;

    @ManyToOne
    @JoinColumn(name = "Cliente_idCliente", nullable = false)
    private Cliente cliente;

    public Pedido() {

    }

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PedidoHasProduto> pedidoHasProdutos;

    // Getter
    public List<PedidoHasProduto> getPedidoHasProdutos() {
        return pedidoHasProdutos;
    }

    // Setter
    public void setPedidoHasProdutos(List<PedidoHasProduto> pedidoHasProdutos) {
        this.pedidoHasProdutos = pedidoHasProdutos;
    }



    // Getters e setters

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public BigDecimal getPedidoValor() {
        return pedidoValor;
    }

    public void setPedidoValor(BigDecimal pedidoValor) {
        this.pedidoValor = pedidoValor;
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