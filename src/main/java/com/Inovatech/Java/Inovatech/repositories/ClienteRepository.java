package com.Inovatech.Java.Inovatech.repositories;

import com.Inovatech.Java.Inovatech.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByEmailCliente(String emailCliente);
}
