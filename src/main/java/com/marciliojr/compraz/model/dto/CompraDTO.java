package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CompraDTO {

    private Long id;
    private String nomeEstabelecimento;
    private LocalDate dataCompra;
    private BigDecimal valorTotal;

    public CompraDTO() {
    }


    public CompraDTO(Long id, String nomeEstabelecimento, LocalDate dataCompra, BigDecimal valorTotal) {
        this.id = id;
        this.nomeEstabelecimento = nomeEstabelecimento;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public void setNomeEstabelecimento(String nomeEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
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
        CompraDTO compraDTO = (CompraDTO) o;
        return Objects.equals(id, compraDTO.id) && Objects.equals(nomeEstabelecimento, compraDTO.nomeEstabelecimento) && Objects.equals(dataCompra, compraDTO.dataCompra) && Objects.equals(valorTotal, compraDTO.valorTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeEstabelecimento, dataCompra, valorTotal);
    }

    @Override
    public String toString() {
        return "CompraDTO{" +
                "id=" + id +
                ", nomeEstabelecimento='" + nomeEstabelecimento + '\'' +
                ", dataCompra=" + dataCompra +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
