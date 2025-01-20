package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaVaziaSeNaoHouverItens() {
        when(itemService.listarItensPorDataEstabelecimento("Mercado", LocalDate.of(2024, 1, 10)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ItemDTO>> resposta = itemController.buscarItensPorEstabelecimentoEData("Mercado", "2024-01-10");

        assertEquals(200, resposta.getStatusCodeValue());
        assertNotNull(resposta.getBody());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void deveRetornarErroParaDataInvalida() {
        ResponseEntity<List<ItemDTO>> resposta = itemController.buscarItensPorEstabelecimentoEData("Mercado", "data-invalida");

        assertEquals(400, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }
}
