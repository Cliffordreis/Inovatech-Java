package com.Inovatech.StatusPedido.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedido {
    private Integer pedidoId;
    private String statusDescricao;
    private LocalDateTime ultimaAtualizacao;


    @Override
    public String toString() {
        return "StatusPedido{" +
                "pedidoId=" + pedidoId +
                ", statusDescricao='" + statusDescricao + '\'' +
                ", ultimaAtualizacao=" + ultimaAtualizacao +
                '}';
    }
}
