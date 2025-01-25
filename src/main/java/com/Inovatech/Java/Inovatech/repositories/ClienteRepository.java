package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByEmailCliente(String emailCliente);
}
