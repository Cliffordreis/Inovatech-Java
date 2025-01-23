package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente_idCliente(Integer Cliente_idCliente);
    @Query("SELECT p FROM Pedido p JOIN FETCH p.pedidoHasProdutos php JOIN FETCH php.produto WHERE p.cliente.idCliente = :clienteId")
    List<Pedido> findPedidosComProdutos(@Param("clienteId") Integer clienteId);

}
