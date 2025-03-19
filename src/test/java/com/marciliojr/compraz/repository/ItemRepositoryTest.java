package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Disabled
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    void deveSalvarERecuperarItem() {
//        // Criando estabelecimento e compra
//        Estabelecimento estabelecimento = new Estabelecimento();
//        estabelecimento.setNomeEstabelecimento("Padaria Central");
//        estabelecimento = estabelecimentoRepository.save(estabelecimento);
//
//        Compra compra = new Compra();
//        compra.setEstabelecimento(estabelecimento);
//        compra.setDataCompra(LocalDate.now());
//        compra = compraRepository.save(compra);
//
//        // Criando item associado à compra
//        Item item = new Item();
//        item.setNome("Pão Francês");
//        item.setQuantidade(new BigDecimal("1.5"));
//        item.setUnidade("kg");
//        item.setValorUnitario(new BigDecimal("8.50"));
//        item.setValorTotal(new BigDecimal("12.75"));
//        item.setCompra(compra);
//        item = itemRepository.save(item);
//
//        // Buscando item salvo
//        List<Item> itens = itemRepository.findAll();
//        assertThat(itens).hasSize(1);
//        assertThat(itens.get(0).getNome()).isEqualTo("Pão Francês");
    }

    @Test
    void deveBuscarItensPorEstabelecimentoEPeriodo() {
//        // Criando estabelecimento e compra
//        Estabelecimento estabelecimento = new Estabelecimento();
//        estabelecimento.setNomeEstabelecimento("Mercado Z");
//        estabelecimento = estabelecimentoRepository.save(estabelecimento);
//
//        Compra compra = new Compra();
//        compra.setEstabelecimento(estabelecimento);
//        compra.setDataCompra(LocalDate.of(2024, 1, 10));
//        compra = compraRepository.save(compra);
//
//        // Criando item associado à compra
//        Item item = new Item();
//        item.setNome("Leite");
//        item.setQuantidade(new BigDecimal("2"));
//        item.setUnidade("Litros");
//        item.setValorUnitario(new BigDecimal("5.00"));
//        item.setValorTotal(new BigDecimal("10.00"));
//        item.setCompra(compra);
//        item = itemRepository.save(item);
//
//        // Buscando itens pelo período e estabelecimento
//        List<ItemDTO> itensFiltrados = itemRepository.findAllItemsByEstabelecimentoAndPeriodo("Mercado Z", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
//
//        assertThat(itensFiltrados).hasSize(1);
//        assertThat(itensFiltrados.get(0).getNome()).isEqualTo("Leite");
    }
}
