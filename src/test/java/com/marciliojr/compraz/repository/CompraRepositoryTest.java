package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.model.dto.CompraRelatorioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CompraRepositoryTest {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private Estabelecimento estabelecimento;
    private Compra compra;
    private Item item;

    @BeforeEach
    void setUp() {

        estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        compra = new Compra();
        compra.setEstabelecimento(estabelecimento);
        compra.setDataCompra(LocalDate.now());

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
    void deveBuscarComprasPorEstabelecimentoEPeriodo() {

        List<CompraDTO> compras = compraRepository.findByNomeEstabelecimentoDataCompraValorTotal(
                "Mercado Teste",
                TipoCupom.MERCADO,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        assertThat(compras).hasSize(1);
        CompraDTO compraDTO = compras.get(0);
        assertThat(compraDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(compraDTO.getDataCompra()).isEqualTo(LocalDate.now());
        assertThat(compraDTO.getValorTotal().intValue()).isEqualTo(BigDecimal.TEN.intValue());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoEncontrarCompras() {

        List<CompraDTO> compras = compraRepository.findByNomeEstabelecimentoDataCompraValorTotal(
                "Estabelecimento Inexistente",
                TipoCupom.MERCADO,
                LocalDate.now(),
                LocalDate.now()
        );


        assertThat(compras).isEmpty();
    }

    @Test
    void deveBuscarCompraPorEstabelecimentoEData() {

        Optional<CompraDTO> compraEncontrada = compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste", LocalDate.now());


        assertThat(compraEncontrada).isPresent();
        CompraDTO compraDTO = compraEncontrada.get();
        assertThat(compraDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(compraDTO.getDataCompra()).isEqualTo(LocalDate.now());
        assertThat(compraDTO.getValorTotal().intValue()).isEqualTo(BigDecimal.TEN.intValue());
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrarCompraPorEstabelecimentoEData() {

        Optional<CompraDTO> compraEncontrada = compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Estabelecimento Inexistente", LocalDate.now());

        assertThat(compraEncontrada).isEmpty();
    }

    @Test
    void deveBuscarRelatorioPorPeriodo() {
        List<CompraRelatorioDTO> relatorio = compraRepository.findRelatorioByDataCompra(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        );

        assertThat(relatorio).hasSize(1);
        CompraRelatorioDTO relatorioDTO = relatorio.get(0);
        assertThat(relatorioDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(relatorioDTO.getValorTotal().intValue()).isEqualTo(BigDecimal.TEN.intValue());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoEncontrarRelatorioNoPeriodo() {
        List<CompraRelatorioDTO> relatorio = compraRepository.findRelatorioByDataCompra(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );

        assertThat(relatorio).isEmpty();
    }

    @Test
    void deveVerificarSeExisteCompraPorEstabelecimento() {
        Boolean existe = compraRepository.existsByEstabelecimentoId(estabelecimento.getId());
        assertThat(existe).isTrue();
    }

    @Test
    void deveRetornarFalseQuandoNaoExisteCompraPorEstabelecimento() {
        Boolean existe = compraRepository.existsByEstabelecimentoId(999L);
        assertThat(existe).isFalse();
    }
} 