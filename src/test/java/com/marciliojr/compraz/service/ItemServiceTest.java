package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    void setUp() {
        item = new Item();
        item.setId(1L);
        item.setNome("Arroz");
        item.setQuantidade(BigDecimal.valueOf(5));
        item.setUnidade("kg");
        item.setValorUnitario(BigDecimal.valueOf(20));
    }

    @Test
    @Disabled
    void listarTodos_DeveRetornarListaDeItens() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item));

        List<ItemDTO> result = itemService.listarTodos();

        assertEquals(1, result.size());
        assertEquals("Arroz", result.get(0).getNome());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void listarItensPorEstabelecimentoEPeriodo_DeveRetornarListaFiltrada() {
        String estabelecimento = "Mercado";
        LocalDate dataInicio = LocalDate.of(2023, 1, 1);
        LocalDate dataFim = LocalDate.of(2023, 12, 31);
        ItemDTO itemDTO = new ItemDTO(1L, "Arroz", BigDecimal.valueOf(5), "kg", BigDecimal.valueOf(20), LocalDate.now(), estabelecimento);

        when(itemRepository.findAllItemsByEstabelecimentoAndPeriodo(estabelecimento, dataInicio, dataFim))
                .thenReturn(Arrays.asList(itemDTO));

        List<ItemDTO> result = itemService.listarItensPorEstabelecimentoEPeriodo(estabelecimento, dataInicio, dataFim);

        assertEquals(1, result.size());
        assertEquals("Arroz", result.get(0).getNome());
        verify(itemRepository, times(1)).findAllItemsByEstabelecimentoAndPeriodo(estabelecimento, dataInicio, dataFim);
    }
}
