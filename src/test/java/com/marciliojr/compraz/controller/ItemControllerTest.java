package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private ItemDTO itemDTO;

    @BeforeEach
    void setUp() {
        itemDTO = new ItemDTO(1L, "Feijão", BigDecimal.valueOf(2), "kg", BigDecimal.valueOf(10), LocalDate.now(), "Mercado");
    }

    @Test
    void buscarItensPorEstabelecimentoEPeriodo_DeveRetornarListaDeItens() {
        String nomeEstabelecimento = "Mercado";
        String dataInicio = "2023-01-01";
        String dataFim = "2023-12-31";

        when(itemService.listarItensPorEstabelecimentoEPeriodo(eq(nomeEstabelecimento), any(), any()))
                .thenReturn(Arrays.asList(itemDTO));

        ResponseEntity<List<ItemDTO>> response = itemController.buscarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, dataInicio, dataFim);

        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Feijão", response.getBody().get(0).getNome());
        verify(itemService, times(1)).listarItensPorEstabelecimentoEPeriodo(eq(nomeEstabelecimento), any(), any());
    }
}
