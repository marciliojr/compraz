package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class RelatorioComparativoDTO {
    private String nomeItem;
    private List<ComparacaoEstabelecimentoDTO> comparacoesMesAtual;
    private List<ComparacaoEstabelecimentoDTO> comparacoesMesAnterior;
    
    public RelatorioComparativoDTO() {
    }
    
    public RelatorioComparativoDTO(String nomeItem, 
                                   List<ComparacaoEstabelecimentoDTO> comparacoesMesAtual,
                                   List<ComparacaoEstabelecimentoDTO> comparacoesMesAnterior) {
        this.nomeItem = nomeItem;
        this.comparacoesMesAtual = comparacoesMesAtual;
        this.comparacoesMesAnterior = comparacoesMesAnterior;
    }
    
    public String getNomeItem() {
        return nomeItem;
    }
    
    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    
    public List<ComparacaoEstabelecimentoDTO> getComparacoesMesAtual() {
        return comparacoesMesAtual;
    }
    
    public void setComparacoesMesAtual(List<ComparacaoEstabelecimentoDTO> comparacoesMesAtual) {
        this.comparacoesMesAtual = comparacoesMesAtual;
    }
    
    public List<ComparacaoEstabelecimentoDTO> getComparacoesMesAnterior() {
        return comparacoesMesAnterior;
    }
    
    public void setComparacoesMesAnterior(List<ComparacaoEstabelecimentoDTO> comparacoesMesAnterior) {
        this.comparacoesMesAnterior = comparacoesMesAnterior;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatorioComparativoDTO that = (RelatorioComparativoDTO) o;
        return Objects.equals(nomeItem, that.nomeItem) &&
                Objects.equals(comparacoesMesAtual, that.comparacoesMesAtual) &&
                Objects.equals(comparacoesMesAnterior, that.comparacoesMesAnterior);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nomeItem, comparacoesMesAtual, comparacoesMesAnterior);
    }
    
    @Override
    public String toString() {
        return "RelatorioComparativoDTO{" +
                "nomeItem='" + nomeItem + '\'' +
                ", comparacoesMesAtual=" + comparacoesMesAtual +
                ", comparacoesMesAnterior=" + comparacoesMesAnterior +
                '}';
    }

    public static class ComparacaoEstabelecimentoDTO {
        private String nomeEstabelecimento;
        private BigDecimal precoMedio;
        private BigDecimal quantidadeComprada;
        private Integer totalCompras;
        
        public ComparacaoEstabelecimentoDTO() {
        }
        
        public ComparacaoEstabelecimentoDTO(String nomeEstabelecimento, 
                                           BigDecimal precoMedio,
                                           BigDecimal quantidadeComprada,
                                           Integer totalCompras) {
            this.nomeEstabelecimento = nomeEstabelecimento;
            this.precoMedio = precoMedio;
            this.quantidadeComprada = quantidadeComprada;
            this.totalCompras = totalCompras;
        }
        
        public String getNomeEstabelecimento() {
            return nomeEstabelecimento;
        }
        
        public void setNomeEstabelecimento(String nomeEstabelecimento) {
            this.nomeEstabelecimento = nomeEstabelecimento;
        }
        
        public BigDecimal getPrecoMedio() {
            return precoMedio;
        }
        
        public void setPrecoMedio(BigDecimal precoMedio) {
            this.precoMedio = precoMedio;
        }
        
        public BigDecimal getQuantidadeComprada() {
            return quantidadeComprada;
        }
        
        public void setQuantidadeComprada(BigDecimal quantidadeComprada) {
            this.quantidadeComprada = quantidadeComprada;
        }
        
        public Integer getTotalCompras() {
            return totalCompras;
        }
        
        public void setTotalCompras(Integer totalCompras) {
            this.totalCompras = totalCompras;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComparacaoEstabelecimentoDTO that = (ComparacaoEstabelecimentoDTO) o;
            return Objects.equals(nomeEstabelecimento, that.nomeEstabelecimento) &&
                    Objects.equals(precoMedio, that.precoMedio) &&
                    Objects.equals(quantidadeComprada, that.quantidadeComprada) &&
                    Objects.equals(totalCompras, that.totalCompras);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(nomeEstabelecimento, precoMedio, quantidadeComprada, totalCompras);
        }
        
        @Override
        public String toString() {
            return "ComparacaoEstabelecimentoDTO{" +
                    "nomeEstabelecimento='" + nomeEstabelecimento + '\'' +
                    ", precoMedio=" + precoMedio +
                    ", quantidadeComprada=" + quantidadeComprada +
                    ", totalCompras=" + totalCompras +
                    '}';
        }
    }
} 