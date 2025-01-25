package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
