package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PDFDadosServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private PDFDadosService pdfDadosService;

    private String textoPDF;
    private String nomeEstabelecimento;
    private LocalDate dataCompra;
    private Estabelecimento estabelecimento;
    private Compra compra;
    private CompraDTO compraDTO;

    @BeforeEach
    void setUp() {
        textoPDF = "Produto Teste (Código: 123) Qtde.: 1 UN: UN Vl. Unit.: 10.00 Vl. Total 10.00";
        nomeEstabelecimento = "Mercado Teste";
        dataCompra = LocalDate.now();

        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setNomeEstabelecimento(nomeEstabelecimento);
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);

        compra = new Compra();
        compra.setId(1L);
        compra.setEstabelecimento(estabelecimento);
        compra.setDataCompra(dataCompra);

        compraDTO = new CompraDTO(1L, nomeEstabelecimento, dataCompra, BigDecimal.TEN);
    }

    @Test
    void deveProcessarDadosEPersistirNovoEstabelecimentoECompra() {

        when(estabelecimentoRepository.findByNomeEstabelecimento(nomeEstabelecimento))
                .thenReturn(Optional.empty());
        when(estabelecimentoRepository.save(any(Estabelecimento.class)))
                .thenReturn(estabelecimento);
        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class)))
                .thenReturn(Optional.empty());
        when(compraRepository.save(any(Compra.class)))
                .thenReturn(compra);

        pdfDadosService.processarDadosEPersistir(textoPDF, nomeEstabelecimento, dataCompra, TipoCupom.MERCADO);

        verify(estabelecimentoRepository).findByNomeEstabelecimento(nomeEstabelecimento);
        verify(estabelecimentoRepository).save(any(Estabelecimento.class));
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class));
        verify(compraRepository).save(any(Compra.class));
    }

    @Test
    void deveProcessarDadosEPersistirComEstabelecimentoExistente() {

        when(estabelecimentoRepository.findByNomeEstabelecimento(nomeEstabelecimento))
                .thenReturn(Optional.of(estabelecimento));
        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class)))
                .thenReturn(Optional.empty());
        when(compraRepository.save(any(Compra.class)))
                .thenReturn(compra);

        pdfDadosService.processarDadosEPersistir(textoPDF, nomeEstabelecimento, dataCompra, TipoCupom.MERCADO);

        verify(estabelecimentoRepository).findByNomeEstabelecimento(nomeEstabelecimento);
        verify(estabelecimentoRepository, never()).save(any(Estabelecimento.class));
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class));
        verify(compraRepository).save(any(Compra.class));
    }

    @Test
    void deveProcessarDadosEPersistirComCompraExistente() {

        when(estabelecimentoRepository.findByNomeEstabelecimento(nomeEstabelecimento))
                .thenReturn(Optional.of(estabelecimento));
        when(compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class)))
                .thenReturn(Optional.of(compraDTO));
        when(compraRepository.findById(1L))
                .thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class)))
                .thenReturn(compra);

        pdfDadosService.processarDadosEPersistir(textoPDF, nomeEstabelecimento, dataCompra, TipoCupom.MERCADO);

        verify(estabelecimentoRepository).findByNomeEstabelecimento(nomeEstabelecimento);
        verify(compraRepository).findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
                eq(nomeEstabelecimento),
                any(LocalDate.class));
        verify(compraRepository).findById(1L);
        verify(compraRepository).save(any(Compra.class));
    }

    @Test
    void deveLancarExcecaoQuandoTextoPDFForVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                pdfDadosService.processarDadosEPersistir("", nomeEstabelecimento, dataCompra, TipoCupom.MERCADO));
    }

    @Test
    void deveLancarExcecaoQuandoNomeEstabelecimentoForVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                pdfDadosService.processarDadosEPersistir(textoPDF, "", dataCompra, TipoCupom.MERCADO));
    }
} 