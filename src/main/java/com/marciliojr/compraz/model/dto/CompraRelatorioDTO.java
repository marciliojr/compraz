package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CompraRelatorioDTO {
    private String nomeEstabelecimento;
    private BigDecimal valorTotal;

    public CompraRelatorioDTO(String nomeEstabelecimento, BigDecimal valorTotal) {
        this.nomeEstabelecimento = nomeEstabelecimento;
        this.valorTotal = valorTotal;
    }

    public CompraRelatorioDTO() {
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public void setNomeEstabelecimento(String nomeEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompraRelatorioDTO that = (CompraRelatorioDTO) o;
        return Objects.equals(nomeEstabelecimento, that.nomeEstabelecimento) && Objects.equals(valorTotal, that.valorTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeEstabelecimento, valorTotal);
    }

    @Override
    public String toString() {
        return "CompraRelatorioDTO{" +
                "nomeEstabelecimento='" + nomeEstabelecimento + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}

