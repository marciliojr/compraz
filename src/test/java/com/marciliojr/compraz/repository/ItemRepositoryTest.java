package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private Estabelecimento estabelecimento;
    private Compra compra;
    private Item item;

    @BeforeEach
    void setUp() {
        // Criar estabelecimento
        estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        // Criar compra
        compra = new Compra();
        compra.setEstabelecimento(estabelecimento);
        compra.setDataCompra(LocalDate.now());

        // Criar item
        item = new Item();
        item.setNome("Produto Teste");
        item.setQuantidade(BigDecimal.ONE);
        item.setUnidade("UN");
        item.setValorUnitario(BigDecimal.TEN);
        item.setValorTotal(BigDecimal.TEN);
        item.setCompra(compra);

        List<Item> itens = new ArrayList<>();
        itens.add(item);
        compra.setItens(itens);

        compra = compraRepository.save(compra);
    }

    @Test
    void deveBuscarItensPorEstabelecimentoEPeriodo() {
        List<ItemDTO> itens = itemRepository.findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        assertThat(itens).hasSize(1);
        ItemDTO itemDTO = itens.get(0);
        assertThat(itemDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(itemDTO.getQuantidade().intValue()).isEqualTo(BigDecimal.ONE.intValue());
        assertThat(itemDTO.getUnidade()).isEqualTo("UN");
        assertThat(itemDTO.getValorUnitario().intValue()).isEqualTo(BigDecimal.TEN.intValue());
        assertThat(itemDTO.getValorTotal().intValue()).isEqualTo(BigDecimal.TEN.intValue());
        assertThat(itemDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoEncontrarItens() {
        List<ItemDTO> itens = itemRepository.findAllItemsByEstabelecimentoAndPeriodo(
                "Estabelecimento Inexistente",
                TipoCupom.MERCADO,
                LocalDate.now(),
                LocalDate.now()
        );

        assertThat(itens).isEmpty();
    }

    @Test
    void deveSomarValorTotalPorEstabelecimentoEPeriodo() {
        BigDecimal valorTotal = itemRepository.sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        assertThat(valorTotal.intValue()).isEqualTo(BigDecimal.TEN.intValue());
    }

    @Test
    void deveBuscarItensPorNomeEPeriodo() {
        List<ItemDTO> itens = itemRepository.findByNomeByPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                "Mercado"
        );

        assertThat(itens).hasSize(1);
        ItemDTO itemDTO = itens.get(0);
        assertThat(itemDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(itemDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
    }

    @Test
    void deveBuscarItensPorCompraId() {
        List<ItemDTO> itens = itemRepository.findByCompraId(compra.getId());

        assertThat(itens).hasSize(1);
        ItemDTO itemDTO = itens.get(0);
        assertThat(itemDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(itemDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
    }

    @Test
    void deveDeletarItensPorCompraId() {
        itemRepository.deleteByCompraId(compra.getId());
        List<ItemDTO> itens = itemRepository.findByCompraId(compra.getId());

        assertThat(itens).isEmpty();
    }

    @Test
    void deveBuscarItensPorNomeEPeriodoPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Page<ItemDTO> itens = itemRepository.findByNomeByPeriodoPaginado(
                "Produto",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                "Mercado",
                pageRequest
        );

        assertThat(itens.getContent()).hasSize(1);
        ItemDTO itemDTO = itens.getContent().get(0);
        assertThat(itemDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(itemDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(itens.getTotalElements()).isEqualTo(1);
        assertThat(itens.getTotalPages()).isEqualTo(1);
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoEncontrarItensPaginados() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("nome").ascending());

        Page<ItemDTO> itens = itemRepository.findByNomeByPeriodoPaginado(
                "Produto Inexistente",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                "Mercado",
                pageRequest
        );

        assertThat(itens.getContent()).isEmpty();
        assertThat(itens.getTotalElements()).isEqualTo(0);
        assertThat(itens.getTotalPages()).isEqualTo(0);
    }


}