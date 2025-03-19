package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import com.marciliojr.compraz.service.PDFGeradorProdutos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private PDFGeradorProdutos pdfGeradorProdutos;

    @InjectMocks
    private ItemController itemController;

    private List<ItemDTO> mockItems;

    @BeforeEach
    void setUp() {
//        mockItems = Arrays.asList(
//                new ItemDTO(1L, "Arroz", BigDecimal.valueOf(5), "Kg", BigDecimal.valueOf(10), BigDecimal.valueOf(50), LocalDate.of(2024, 1, 20), "Mercado X"),
//                new ItemDTO(2L, "Feijão", BigDecimal.valueOf(2), "Kg", BigDecimal.valueOf(8), BigDecimal.valueOf(16), LocalDate.of(2024, 1, 21), "Mercado Y")
//        );
    }

    @Test
    void buscarItensPorEstabelecimentoEPeriodo_DeveRetornarListaDeItens() {
//        when(itemService.listarItensPorEstabelecimentoEPeriodo("Mercado X", null, null)).thenReturn(mockItems);
//
//        ResponseEntity<List<ItemDTO>> response = itemController.buscarItensPorEstabelecimentoEPeriodo("Mercado X", null, null);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(2, response.getBody().size());
//        verify(itemService, times(1)).listarItensPorEstabelecimentoEPeriodo("Mercado X", null, null);
    }

    @Test
    void somarValorUnitarioPorEstabelecimentoEPeriodo_DeveRetornarSomaCorreta() {
//        when(itemService.somarValorUnitarioPorEstabelecimentoEPeriodo("Mercado X", null, null)).thenReturn(BigDecimal.valueOf(100));
//
//        ResponseEntity<BigDecimal> response = itemController.somarValorUnitarioPorEstabelecimentoEPeriodo("Mercado X", null, null);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(BigDecimal.valueOf(100), response.getBody());
//        verify(itemService, times(1)).somarValorUnitarioPorEstabelecimentoEPeriodo("Mercado X", null, null);
    }


}
