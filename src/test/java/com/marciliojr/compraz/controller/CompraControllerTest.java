package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.model.dto.CompraRelatorioDTO;
import com.marciliojr.compraz.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompraControllerTest {

    @Mock
    private CompraService compraService;

    @InjectMocks
    private CompraController compraController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarCompras_DeveRetornarListaDeCompras() {
        // Arrange
        String nomeEstabelecimento = "Teste";
        Integer tipoCupom = 1;
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = LocalDate.now();
        List<CompraDTO> compras = Arrays.asList(new CompraDTO(), new CompraDTO());

        when(compraService.listarComprasPorEstabelecimentoEPeriodo(
            nomeEstabelecimento, 
            TipoCupom.obterPorCodigo(tipoCupom), 
            dataInicio, 
            dataFim
        )).thenReturn(compras);

        // Act
        ResponseEntity<List<CompraDTO>> response = compraController.listarCompras(
            nomeEstabelecimento, 
            tipoCupom, 
            dataInicio, 
            dataFim
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(compras, response.getBody());
        verify(compraService).listarComprasPorEstabelecimentoEPeriodo(
            nomeEstabelecimento, 
            TipoCupom.obterPorCodigo(tipoCupom), 
            dataInicio, 
            dataFim
        );
    }

    @Test
    void excluirCompraPorId_DeveRetornarNoContent() {
        // Arrange
        Long id = 1L;
        doNothing().when(compraService).excluirCompraPorId(id);

        // Act
        ResponseEntity<Void> response = compraController.excluirCompraPorId(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(compraService).excluirCompraPorId(id);
    }

    @Test
    void gerarRelatorio_DeveRetornarRelatorio() {
        // Arrange
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = LocalDate.now();
        List<CompraRelatorioDTO> relatorio = Arrays.asList(new CompraRelatorioDTO(), new CompraRelatorioDTO());

        when(compraService.gerarRelatorioPorDataCompra(dataInicio, dataFim)).thenReturn(relatorio);

        // Act
        ResponseEntity<List<CompraRelatorioDTO>> response = compraController.gerarRelatorio(dataInicio, dataFim);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(relatorio, response.getBody());
        verify(compraService).gerarRelatorioPorDataCompra(dataInicio, dataFim);
    }
} 