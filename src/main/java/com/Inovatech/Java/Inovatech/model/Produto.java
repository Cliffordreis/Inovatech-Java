package com.Inovatech.Java.Inovatech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduto;

    @Column(name = "ProdutoDescricao", length = 45)
    private String produtoDescricao;

    @Column(name = "ProdutoValor")
    private Double produtoValor;

    @Column(name = "ProdutoQuantidade")
    private Integer produtoQuantidade;


}
