package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemDTO {
    private Long id;
    private String nome;
    private BigDecimal quantidade;
    private String unidade;
    private BigDecimal valorUnitario;
    private LocalDate dataCompra;
    private String nomeEstabelecimento;

    public ItemDTO() {

    }

    // Construtor
    public ItemDTO(Long id, String nome, BigDecimal quantidade, String unidade, BigDecimal valorUnitario, LocalDate dataCompra, String nomeEstabelecimento) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.valorUnitario = valorUnitario;
        this.dataCompra = dataCompra;
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    public void setNomeEstabelecimento(String nomeEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    public static ItemDTO construirComAtributosBasicos(String nome, BigDecimal quantidade, String unidade, BigDecimal valorUnitario) {
        ItemDTO item = new ItemDTO();
        item.setNome(nome);
        item.setQuantidade(quantidade);
        item.setUnidade(unidade);
        item.setValorUnitario(valorUnitario);
        item.setDataCompra(LocalDate.now());
        return item;
    }


}
