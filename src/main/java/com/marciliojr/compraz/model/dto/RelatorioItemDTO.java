package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class RelatorioItemDTO {
    private String nomeItem;
    private BigDecimal quantidadeTotal;
    private BigDecimal mediaPreco;

    public RelatorioItemDTO() {
    }

    public RelatorioItemDTO(String nomeItem, BigDecimal quantidadeTotal, BigDecimal mediaPreco) {
        this.nomeItem = nomeItem;
        this.quantidadeTotal = quantidadeTotal;
        this.mediaPreco = mediaPreco;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public BigDecimal getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(BigDecimal quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public BigDecimal getMediaPreco() {
        return mediaPreco;
    }

    public void setMediaPreco(BigDecimal mediaPreco) {
        this.mediaPreco = mediaPreco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatorioItemDTO that = (RelatorioItemDTO) o;
        return Objects.equals(nomeItem, that.nomeItem) &&
                Objects.equals(quantidadeTotal, that.quantidadeTotal) &&
                Objects.equals(mediaPreco, that.mediaPreco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeItem, quantidadeTotal, mediaPreco);
    }

    @Override
    public String toString() {
        return "RelatorioItemDTO{" +
                "nomeItem='" + nomeItem + '\'' +
                ", quantidadeTotal=" + quantidadeTotal +
                ", mediaPreco=" + mediaPreco +
                '}';
    }
} 