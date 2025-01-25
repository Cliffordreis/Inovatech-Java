package com.Inovatech.Java.Inovatech.service;

import com.Inovatech.Java.Inovatech.model.StatusCache;
import com.Inovatech.Java.Inovatech.repositories.StatusCacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusCacheService {

    private static StatusCacheRepository statusCacheRepository;

    public StatusCacheService(StatusCacheRepository statusCacheRepository) {
        StatusCacheService.statusCacheRepository = statusCacheRepository;
    }

    public static List<StatusCache> getStatusByPedidoId(Integer pedidoId) {
        return statusCacheRepository.findAllByPedidoId(pedidoId);
    }
}

