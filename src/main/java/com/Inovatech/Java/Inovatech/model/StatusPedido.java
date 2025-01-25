package com.Inovatech.Java.Inovatech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "StatusPedido")
public class StatusPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatusPedido;

    @Column(name = "StatusPedidoDescricao")
    private LocalDateTime statusPedidoDescricao;

    @Column(name = "StatusDataHora")
    private LocalDateTime statusDataHota;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Pedido_idPedido", referencedColumnName = "idPedido"),
            @JoinColumn(name = "Pedido_Cliente_idCliente", referencedColumnName = "Cliente_idCliente")
    })
    private Pedido pedido;

}
