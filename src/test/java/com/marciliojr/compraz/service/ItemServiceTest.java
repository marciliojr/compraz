package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaVaziaSeNaoHouverItens() {
        when(itemRepository.findAll()).thenReturn(Collections.emptyList());

        List<ItemDTO> resultado = itemService.listarTodos();

        assertTrue(resultado.isEmpty());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarItensPorEstabelecimentoEData() {
        String nomeEstabelecimento = "Supermercado X";
        LocalDate data = LocalDate.of(2024, 1, 15);

        when(itemRepository.findAllItemsByEstabelecimentoAndDataCompra(nomeEstabelecimento, data))
                .thenReturn(Collections.emptyList());

        List<ItemDTO> resultado = itemService.listarItensPorDataEstabelecimento(nomeEstabelecimento, data);

        assertTrue(resultado.isEmpty());
        verify(itemRepository, times(1))
                .findAllItemsByEstabelecimentoAndDataCompra(nomeEstabelecimento, data);
    }
}
