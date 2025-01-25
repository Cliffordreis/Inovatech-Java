package com.Inovatech.StatusPedido.controller;

import com.Inovatech.StatusPedido.model.Transportadora;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transportadora")
public class StatusController {

    private final RabbitTemplate rabbitTemplate;

    public StatusController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Producer que envia para a fila p.atuaClif
    @PostMapping("/atualizacao/{pedidoId}")
    public ResponseEntity<String> enviarAtualizacao(@PathVariable Integer pedidoId, @RequestBody Transportadora transportadora) {
        // Atualizando o pedido com os dados recebidos
        transportadora.setPedidoId(pedidoId);
        transportadora.setUltimaAtualizacao(LocalDateTime.now());  // Hora da atualização

        // Log de envio para verificação
        System.out.println("Mensagem recebida para enviar: " + transportadora);

        // Enviar a atualização para o RabbitMQ na fila p.atuaClif
        rabbitTemplate.convertAndSend("p.atuaClif", transportadora);

        return ResponseEntity.ok("Pedido atualizado e mensagem enviada para a fila p.atuaClif.");
    }
}
