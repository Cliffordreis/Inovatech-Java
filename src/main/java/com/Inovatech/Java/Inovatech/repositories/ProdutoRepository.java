package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
