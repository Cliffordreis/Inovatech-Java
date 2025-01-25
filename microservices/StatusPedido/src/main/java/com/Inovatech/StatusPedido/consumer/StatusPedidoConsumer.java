package com.Inovatech.StatusPedido.consumer;

import com.Inovatech.StatusPedido.model.StatusPedido;
import com.Inovatech.StatusPedido.model.Transportadora;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatusPedidoConsumer {

    private final RabbitTemplate rabbitTemplate;

    public StatusPedidoConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @RabbitListener(queues = "${rabbitmq.queue.status}")
    public void receberMensagem(StatusPedido statuspedido) {
        System.out.println("Mensagem enviada: " + statuspedido);

        // Processa os dados recebidos
        System.out.println("Pedido ID: " + statuspedido.getPedidoId());
        System.out.println("Status: " + statuspedido.getStatusDescricao());
        System.out.println("Atualizado em: " + statuspedido.getUltimaAtualizacao());

        // Enviar para o microserviço Java-Inovatech
        rabbitTemplate.convertAndSend("p.atuaClif", statuspedido);
        System.out.println("Mensagem enviada para Java-Inovatech.");
    }
}


