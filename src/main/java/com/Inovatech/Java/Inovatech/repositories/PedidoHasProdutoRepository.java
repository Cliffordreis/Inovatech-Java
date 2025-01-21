package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.entity.PedidoHasProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoHasProdutoRepository extends JpaRepository<PedidoHasProduto, PedidoHasProduto.PedidoHasProdutoId> {
}
