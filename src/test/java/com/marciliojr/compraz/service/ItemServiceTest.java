package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemDTO itemDTO;
    private LocalDate dataCompra;

    @BeforeEach
    void setUp() {
        dataCompra = LocalDate.now();

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);

        Compra compra = new Compra();
        compra.setId(1L);
        compra.setEstabelecimento(estabelecimento);
        compra.setDataCompra(dataCompra);

        item = new Item();
        item.setId(1L);
        item.setNome("Produto Teste");
        item.setQuantidade(BigDecimal.ONE);
        item.setUnidade("UN");
        item.setValorUnitario(BigDecimal.TEN);
        item.setValorTotal(BigDecimal.TEN);
        item.setCompra(compra);

        itemDTO = ItemDTO.construir(
                item.getNome(),
                item.getQuantidade(),
                item.getUnidade(),
                item.getValorTotal(),
                item.getValorUnitario(),
                compra.getDataCompra(),
                estabelecimento.getNomeEstabelecimento()
        );
    }

    @Test
    void deveListarTodosItens() {

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        List<ItemDTO> resultado = itemService.listarTodos();

        assertThat(resultado).hasSize(1);
        ItemDTO resultadoDTO = resultado.get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(resultadoDTO.getQuantidade()).isEqualTo(BigDecimal.ONE);
        assertThat(resultadoDTO.getUnidade()).isEqualTo("UN");
        assertThat(resultadoDTO.getValorUnitario()).isEqualTo(BigDecimal.TEN);
        assertThat(resultadoDTO.getValorTotal()).isEqualTo(BigDecimal.TEN);
        assertThat(resultadoDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(itemRepository).findAll();
    }

    @Test
    void deveListarItensPorEstabelecimentoEPeriodo() {

        when(itemRepository.findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(Collections.singletonList(itemDTO));

        List<ItemDTO> resultado = itemService.listarItensPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).hasSize(1);
        ItemDTO resultadoDTO = resultado.get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }

    @Test
    void deveListarItensPorCompraId() {

        when(itemRepository.findByCompraId(1L)).thenReturn(Collections.singletonList(itemDTO));

        List<ItemDTO> resultado = itemService.listarItensPorCompraId(1L);

        assertThat(resultado).hasSize(1);
        ItemDTO resultadoDTO = resultado.get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findByCompraId(1L);
    }

    @Test
    void deveSomarValorTotalPorEstabelecimentoEPeriodo() {

        when(itemRepository.sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(BigDecimal.TEN);

        BigDecimal resultado = itemService.somarValorTotalPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).isEqualTo(BigDecimal.TEN);
        verify(itemRepository).sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }

    @Test
    void deveListarItensPorNomeEPeriodo() {

        when(itemRepository.findByNomeByPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        )).thenReturn(Collections.singletonList(itemDTO));

        List<ItemDTO> resultado = itemService.listarItensPorNomeEPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        );

        assertThat(resultado).hasSize(1);
        ItemDTO resultadoDTO = resultado.get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findByNomeByPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        );
    }

    @Test
    void deveDeletarItemPorId() {

        itemService.deleteById(1L);

        verify(itemRepository).deleteById(anyLong());
    }

    @Test
    void deveDeletarItensPorCompraId() {

        itemService.deleteByCompraId(1L);

        verify(itemRepository).deleteByCompraId(anyLong());
    }

    @Test
    void deveAtualizarItem() {

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDTO itemParaAtualizar = new ItemDTO(
                1L,
                "Produto Atualizado",
                BigDecimal.ONE,
                "UN",
                BigDecimal.TEN,
                BigDecimal.TEN,
                dataCompra,
                "Mercado Teste"
        );

        itemService.atualizarItem(itemParaAtualizar);

        verify(itemRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deveNormalizarNomes() {

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        itemService.normalizarNomes();

        verify(itemRepository).findAll();
        verify(itemRepository).save(any(Item.class));
    }
} 