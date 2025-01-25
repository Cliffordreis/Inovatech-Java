package com.Inovatech.Java.Inovatech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

    // Setter
    // Getter
    @Setter
    @Getter
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PedidoHasProduto> pedidoHasProdutos;

}