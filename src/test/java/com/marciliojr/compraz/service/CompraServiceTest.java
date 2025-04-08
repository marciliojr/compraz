package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.model.dto.CompraRelatorioDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    private CompraDTO compraDTO;
    private CompraRelatorioDTO compraRelatorioDTO;
    private LocalDate dataCompra;

    @BeforeEach
    void setUp() {
        dataCompra = LocalDate.now();
        compraDTO = new CompraDTO(1L, "Mercado Teste", dataCompra, BigDecimal.TEN);
        compraRelatorioDTO = new CompraRelatorioDTO("Mercado Teste", BigDecimal.TEN);
    }

    @Test
    void deveListarComprasPorEstabelecimentoEPeriodo() {
        List<CompraDTO> compras = Arrays.asList(compraDTO);
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
        assertThat(resultado.get(0).getNomeEstabelecimento()).isEqualTo("Mercado Teste");
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
        verify(compraRepository).deleteById(1L);
    }

    @Test
    void deveBuscarCompraPorEstabelecimentoEData() {
        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        )).thenReturn(java.util.Optional.of(compraDTO));

        CompraDTO resultado = compraService.buscarCompraPorEstabelecimentoEData(
                "Mercado Teste",
                dataCompra
        );

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        );
    }

    @Test
    void deveRetornarNullQuandoBuscarCompraPorEstabelecimentoEDataNaoEncontrada() {
        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        )).thenReturn(java.util.Optional.empty());

        CompraDTO resultado = compraService.buscarCompraPorEstabelecimentoEData(
                "Mercado Teste",
                dataCompra
        );

        assertThat(resultado).isNull();
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                "Mercado Teste",
                dataCompra
        );
    }

    @Test
    void deveGerarRelatorioPorDataCompra() {
        List<CompraRelatorioDTO> relatorios = Arrays.asList(compraRelatorioDTO);
        when(compraRepository.findRelatorioByDataCompra(dataCompra, dataCompra))
                .thenReturn(relatorios);

        List<CompraRelatorioDTO> resultado = compraService.gerarRelatorioPorDataCompra(
                dataCompra,
                dataCompra
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(compraRepository).findRelatorioByDataCompra(dataCompra, dataCompra);
    }

    @Test
    void deveVerificarExistenciaDeComprasPorEstabelecimento() {
        when(compraRepository.existsByEstabelecimentoId(1L)).thenReturn(true);

        boolean resultado = compraService.existeComprasPorEstabelecimento(1L);

        assertThat(resultado).isTrue();
        verify(compraRepository).existsByEstabelecimentoId(1L);
    }
} 