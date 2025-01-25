package com.Inovatech.financeiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmacaoPagamento {
    private Integer pedidoId;
    private String statusDescricao;
    private LocalDateTime ultimaAtualizacao;

}
