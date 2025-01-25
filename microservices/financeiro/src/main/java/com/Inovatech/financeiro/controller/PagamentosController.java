package com.Inovatech.financeiro.controller;

import com.Inovatech.financeiro.model.ConfirmacaoPagamento;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pagamentos")
public class PagamentosController {

    private final RabbitTemplate rabbitTemplate;

    public PagamentosController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/confirmacao/{pedidoId}")
    public ResponseEntity<String> confirmarPagamento(@PathVariable Integer pedidoId, @RequestBody ConfirmacaoPagamento confirmacaoPagamento) {
        // Simulando um pagamento processado
        confirmacaoPagamento.setPedidoId(pedidoId);
        confirmacaoPagamento.setStatusDescricao(confirmacaoPagamento.getStatusDescricao());
        confirmacaoPagamento.setUltimaAtualizacao(LocalDateTime.now());

        // Enviar a confirmação para o RabbitMQ
        rabbitTemplate.convertAndSend("c.confCif", confirmacaoPagamento);

        return ResponseEntity.ok("Pagamento confirmado e mensagem enviada para o RabbitMQ.");
    }
}
