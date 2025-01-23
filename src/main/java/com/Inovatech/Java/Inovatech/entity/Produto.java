package com.Inovatech.Java.Inovatech.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    // Getters e Setters


    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }

    public Double getProdutoValor() {
        return produtoValor;
    }

    public void setProdutoValor(Double produtoValor) {
        this.produtoValor = produtoValor;
    }

    public Integer getProdutoQuantidade() {
        return produtoQuantidade;
    }

    public void setProdutoQuantidade(Integer produtoQuantidade) {
        this.produtoQuantidade = produtoQuantidade;
    }
}
