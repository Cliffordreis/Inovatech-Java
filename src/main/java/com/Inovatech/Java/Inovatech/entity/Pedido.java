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

    // Getters e setters
}