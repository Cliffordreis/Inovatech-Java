package com.Inovatech.Java.Inovatech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@Entity
@Table(name = "Cliente")
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    private String nomeCliente;
    private String sobrenomeCliente;
    private String cpfCliente;
    private String emailCliente;
    private String senhaCliente;

    public Cliente() {

    }

}
