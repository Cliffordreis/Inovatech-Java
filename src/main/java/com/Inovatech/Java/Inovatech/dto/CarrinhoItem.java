package com.Inovatech.Java.Inovatech.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarrinhoItem {
    private Integer produtoId;
    private String nome;
    private BigDecimal precoUnitario;
    private int quantidade;
    private BigDecimal subtotal;

}
