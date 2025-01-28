package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item1, item2;
    private Compra compra;

    @BeforeEach
    void setUp() {
        compra = new Compra();
        compra.setDataCompra(LocalDate.of(2024, 1, 20));

        item1 = new Item(1L, "Arroz", BigDecimal.valueOf(5), "Kg", BigDecimal.valueOf(10), BigDecimal.valueOf(50), compra);
        item2 = new Item(2L, "Feijão", BigDecimal.valueOf(2), "Kg", BigDecimal.valueOf(8), BigDecimal.valueOf(16), compra);
    }

    @Test
    void listarTodos_DeveRetornarListaDeItensDTO() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<ItemDTO> resultado = itemService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Arroz", resultado.get(0).getNome());
        assertEquals("Feijão", resultado.get(1).getNome());

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void listarItensPorEstabelecimentoEPeriodo_DeveRetornarItensFiltrados() {
        when(itemRepository.findAllItemsByEstabelecimentoAndPeriodo("Mercado X", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .thenReturn(Arrays.asList(new ItemDTO(1L, "Arroz", BigDecimal.valueOf(5), "Kg", BigDecimal.valueOf(10), BigDecimal.valueOf(50), LocalDate.of(2024, 1, 20), "Mercado X")));

        List<ItemDTO> resultado = itemService.listarItensPorEstabelecimentoEPeriodo("Mercado X", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        assertEquals(1, resultado.size());
        assertEquals("Arroz", resultado.get(0).getNome());
    }

    @Test
    void somarValorUnitarioPorEstabelecimentoEPeriodo_DeveRetornarSomaCorreta() {
        when(itemRepository.sumValorTotalByEstabelecimentoAndPeriodo("Mercado X", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31)))
                .thenReturn(BigDecimal.valueOf(100));

        BigDecimal resultado = itemService.somarValorUnitarioPorEstabelecimentoEPeriodo("Mercado X", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        assertEquals(BigDecimal.valueOf(100), resultado);
    }
}
