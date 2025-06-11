package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class TopProdutosDTO {
    private String nome;
    private BigDecimal quantidadeTotal;
    
    public TopProdutosDTO() {
    }
    
    public TopProdutosDTO(String nome, BigDecimal quantidadeTotal) {
        this.nome = nome;
        this.quantidadeTotal = quantidadeTotal;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public BigDecimal getQuantidadeTotal() {
        return quantidadeTotal;
    }
    
    public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopProdutosDTO that = (TopProdutosDTO) o;
        return Objects.equals(nome, that.nome) && 
               Objects.equals(quantidadeTotal, that.quantidadeTotal);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome, quantidadeTotal);
    }
    
    @Override
    public String toString() {
        return "TopProdutosDTO{" +
                "nome='" + nome + '\'' +
                ", quantidadeTotal=" + quantidadeTotal +
                '}';
    }
} 