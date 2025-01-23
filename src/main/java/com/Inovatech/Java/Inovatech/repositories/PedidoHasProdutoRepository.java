package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.entity.PedidoHasProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoHasProdutoRepository extends JpaRepository<PedidoHasProduto, PedidoHasProduto.PedidoHasProdutoId> {
    List<PedidoHasProduto> findByPedido_Cliente_idCliente(Integer Pedido_Cliente_idCliente);
}
