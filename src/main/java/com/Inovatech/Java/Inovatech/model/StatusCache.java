package com.Inovatech.Java.Inovatech.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Aqui você configura o auto-increment
    private Integer id;

    @Column(name = "pedidoId", nullable = false)
    private Integer pedidoId;

    @Column(name = "statusDescricao", nullable = false)
    private String statusDescricao;

    @Column(name = "ultimaAtualizacao", nullable = false)
    private LocalDateTime ultimaAtualizacao;

}

