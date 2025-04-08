package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    private CompraDTO compraDTO;
    private LocalDate dataCompra;

    @BeforeEach
    void setUp() {
        dataCompra = LocalDate.now();
        compraDTO = new CompraDTO(1L, "Mercado Teste", dataCompra, BigDecimal.TEN);
    }

    @Test
    void deveListarComprasPorEstabelecimentoEPeriodo() {

        List<CompraDTO> compras = Collections.singletonList(compraDTO);
        when(compraRepository.findByNomeEstabelecimentoDataCompraValorTotal(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(compras);

        List<CompraDTO> resultado = compraService.listarComprasPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).hasSize(1);
        CompraDTO resultadoDTO = resultado.get(0);
        assertThat(resultadoDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(resultadoDTO.getDataCompra()).isEqualTo(dataCompra);
        assertThat(resultadoDTO.getValorTotal()).isEqualTo(BigDecimal.TEN);
        verify(compraRepository).findByNomeEstabelecimentoDataCompraValorTotal(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }

    @Test
    void deveExcluirCompraPorId() {

        compraService.excluirCompraPorId(1L);

        verify(compraRepository).deleteById(anyLong());
    }

    @Test
    void deveBuscarCompraPorEstabelecimentoEData() {

        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        )).thenReturn(Optional.of(compraDTO));

        CompraDTO resultado = compraService.buscarCompraPorEstabelecimentoEData(
                "Mercado Teste",
                dataCompra
        );

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(resultado.getDataCompra()).isEqualTo(dataCompra);
        assertThat(resultado.getValorTotal()).isEqualTo(BigDecimal.TEN);
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        );
    }

    @Test
    void deveRetornarNullQuandoNaoEncontrarCompra() {

        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Estabelecimento Inexistente",
                dataCompra
        )).thenReturn(Optional.empty());

        CompraDTO resultado = compraService.buscarCompraPorEstabelecimentoEData(
                "Estabelecimento Inexistente",
                dataCompra
        );

        assertThat(resultado).isNull();
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Estabelecimento Inexistente",
                dataCompra
        );
    }

    @Test
    void deveSomarValorTotalPorEstabelecimentoEPeriodo() {
        when(compraRepository.sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(BigDecimal.TEN);

        BigDecimal resultado = compraService.somarValorTotalPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).isEqualTo(BigDecimal.TEN);
        verify(compraRepository).sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }

    @Test
    void deveRetornarZeroQuandoNaoHouverComprasNoPeriodo() {
        when(compraRepository.sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Inexistente",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(null);

        BigDecimal resultado = compraService.somarValorTotalPorEstabelecimentoEPeriodo(
                "Mercado Inexistente",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).isEqualTo(BigDecimal.ZERO);
        verify(compraRepository).sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Inexistente",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }
} 