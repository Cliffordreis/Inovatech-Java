package com.Inovatech.Java.Inovatech.consumer;

import com.Inovatech.Java.Inovatech.model.StatusCache;
import com.Inovatech.Java.Inovatech.repositories.StatusCacheRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class StatusCacheConsumer {

    private final StatusCacheRepository statusCacheRepository;

    public StatusCacheConsumer(StatusCacheRepository statusCacheRepository) {
        this.statusCacheRepository = statusCacheRepository;
    }

    // Escutando a fila e salvando na tabela StatusCache
    @RabbitListener(queues = "p.atuaClif")
    public void salvarStatusCache(StatusCache statusCache) {
        System.out.println("Mensagem recebida do statuspedido: " + statusCache);

        // Salvar o status na tabela StatusCache
        try {
            statusCacheRepository.save(statusCache);
            System.out.println("Status salvo na tabela StatusCache.");
        } catch (Exception e) {
            System.out.println("Status não salvo " + e);
            throw new RuntimeException(e);
        }
    }
}

