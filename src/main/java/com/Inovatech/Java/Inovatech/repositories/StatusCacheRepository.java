package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.model.Pedido;
import com.Inovatech.Java.Inovatech.model.StatusCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusCacheRepository extends JpaRepository<StatusCache, Integer> {
    List<StatusCache> findAllByPedidoId(Pedido pedido);
}
