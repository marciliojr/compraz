package com.marciliojr.compraz.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe DTO (Data Transfer Object) para representar itens com dados essenciais
 * e agregados de outras entidades, como data da compra e nome do estabelecimento.
 */
public class ItemDTO {

    private Long id;
    private String nome;
    private BigDecimal quantidade;
    private String unidade;
    private BigDecimal valorUnitario;
    private LocalDate dataCompra;
    private String nomeEstabelecimento;

    /**
     * Construtor padrão.
     */
    public ItemDTO() {
    }

    /**
     * Construtor com parâmetros para inicializar todos os campos.
     *
     * @param id                 Identificador único do item.
     * @param nome               Nome do item.
     * @param quantidade         Quantidade do item.
     * @param unidade            Unidade de medida do item (ex.: "kg", "un").
     * @param valorUnitario      Valor unitário do item.
     * @param dataCompra         Data da compra associada ao item.
     * @param nomeEstabelecimento Nome do estabelecimento onde o item foi comprado.
     */
    public ItemDTO(Long id, String nome, BigDecimal quantidade, String unidade, BigDecimal valorUnitario,
                   LocalDate dataCompra, String nomeEstabelecimento) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.valorUnitario = valorUnitario;
        this.dataCompra = dataCompra;
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    // Getters e Setters

    /**
     * @return Identificador único do item.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador do item.
     *
     * @param id Identificador único.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Nome do item.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do item.
     *
     * @param nome Nome do item.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return Quantidade do item.
     */
    public BigDecimal getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do item.
     *
     * @param quantidade Quantidade do item.
     */
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return Unidade de medida do item.
     */
    public String getUnidade() {
        return unidade;
    }

    /**
     * Define a unidade de medida do item.
     *
     * @param unidade Unidade de medida (ex.: "kg", "un").
     */
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    /**
     * @return Valor unitário do item.
     */
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Define o valor unitário do item.
     *
     * @param valorUnitario Valor unitário.
     */
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * @return Data da compra associada ao item.
     */
    public LocalDate getDataCompra() {
        return dataCompra;
    }

    /**
     * Define a data da compra associada ao item.
     *
     * @param dataCompra Data da compra.
     */
    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    /**
     * @return Nome do estabelecimento onde o item foi comprado.
     */
    public String getNomeEstabelecimento() {
        return nomeEstabelecimento;
    }

    /**
     * Define o nome do estabelecimento onde o item foi comprado.
     *
     * @param nomeEstabelecimento Nome do estabelecimento.
     */
    public void setNomeEstabelecimento(String nomeEstabelecimento) {
        this.nomeEstabelecimento = nomeEstabelecimento;
    }

    /**
     * Método de fábrica para criar um ItemDTO com os atributos básicos.
     *
     * @param nome          Nome do item.
     * @param quantidade    Quantidade do item.
     * @param unidade       Unidade de medida do item (ex.: "kg", "un").
     * @param valorUnitario Valor unitário do item.
     * @return Uma instância de ItemDTO com os atributos básicos e a data atual como data de compra.
     */
    public static ItemDTO construirComAtributosBasicos(String nome, BigDecimal quantidade, String unidade,
                                                       BigDecimal valorUnitario) {
        ItemDTO item = new ItemDTO();
        item.setNome(nome);
        item.setQuantidade(quantidade);
        item.setUnidade(unidade);
        item.setValorUnitario(valorUnitario);
        item.setDataCompra(LocalDate.now()); // Define a data atual como padrão.
        return item;
    }
}
