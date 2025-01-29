package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.model.Pedido;
import com.Inovatech.Java.Inovatech.model.StatusCache;
import com.Inovatech.Java.Inovatech.repositories.StatusCacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusCacheService {

    private final StatusCacheRepository statusCacheRepository;

    // Injeção de dependência via o construtor
    public StatusCacheService(StatusCacheRepository statusCacheRepository) {
        this.statusCacheRepository = statusCacheRepository;
    }

    // Método para buscar o StatusCache por Pedido
    public List<StatusCache> getStatusByPedido(Pedido pedido) {
        return statusCacheRepository.findAllByPedidoId(pedido);  // Usando o objeto Pedido
    }
}


