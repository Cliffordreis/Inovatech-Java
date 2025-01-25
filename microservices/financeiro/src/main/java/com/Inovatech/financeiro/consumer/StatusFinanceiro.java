package com.Inovatech.financeiro.consumer;

import com.Inovatech.financeiro.model.Financeiro;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class StatusFinanceiro {

    @RabbitListener(queues = "${rabbitmq.queue.status}")
    public void receberMensagem(Financeiro statusPedido) {
        System.out.println("Mensagem recebida: " + statusPedido);

        // Processa os dados recebidos
        System.out.println("pedidoId: " + statusPedido.getPedidoId());
        System.out.println("statusDescricao: " + statusPedido.getStatusDescricao());
        System.out.println("ultimaAtualizacao: " + statusPedido.getUltimaAtualizacao());
    }
}


