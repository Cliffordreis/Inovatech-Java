package com.Inovatech.Java.Inovatech.model;

import com.Inovatech.Java.Inovatech.deserializer.PedidoDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StatusCache")
public class StatusCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pedidoId", referencedColumnName = "idPedido", nullable = false)  // Correção aqui
    @JsonDeserialize(using = PedidoDeserializer.class) // Aplica o deserializador personalizado
    private Pedido pedidoId;  // Relacionamento com Pedido

    @Column(name = "statusDescricao", nullable = false)
    private String statusDescricao;

    @Column(name = "ultimaAtualizacao", nullable = false)
    private LocalDateTime ultimaAtualizacao;


}

