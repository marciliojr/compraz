package com.marciliojr.compraz.mock;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EntidadesMockBuilder {


    public static Estabelecimento criarEstabelecimento() {
        Estabelecimento e = new Estabelecimento();
        e.setNomeEstabelecimento("Estabelecimento Teste");
        return e;
    }

    public static Estabelecimento criarEstabelecimento(String nomeEstabelecimento) {
        Estabelecimento e = new Estabelecimento();
        e.setNomeEstabelecimento(nomeEstabelecimento);
        return e;
    }

    public static Compra criarCompra() {
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setEstabelecimento(criarEstabelecimento());
        return compra;
    }

    public static Compra criarCompra(Estabelecimento estabelecimento) {
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setEstabelecimento(estabelecimento);
        return compra;
    }

    public static Item criarItem() {

        Item item = new Item();
        item.setValorUnitario(BigDecimal.TEN);
        item.setUnidade("kg");
        item.setQuantidade(BigDecimal.ONE);
        item.setCompra(criarCompra());
        return item;
    }


}
