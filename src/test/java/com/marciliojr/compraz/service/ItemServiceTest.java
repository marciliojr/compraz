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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void deveListarTodosItemDTO() {
        List<Item> items = Collections.singletonList(item);
        when(itemRepository.findAll()).thenReturn(items);

        List<ItemDTO> resultado = itemService.listarTodosItemDTO();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findAll();
    }

    @Test
    void deveListarItensPorEstabelecimentoEPeriodoComTodosParametrosNulos() {

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        List<ItemDTO> resultado = itemService.listarItensPorEstabelecimentoEPeriodo(null, null, null, null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findAll();
    }

    @Test
    void deveListarItensPorEstabelecimentoEPeriodoComParametros() {
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        when(itemRepository.findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(items);

        List<ItemDTO> resultado = itemService.listarItensPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        );
    }

    @Test
    void deveListarItensPorCompraId() {
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        when(itemRepository.findByCompraId(1L)).thenReturn(items);

        List<ItemDTO> resultado = itemService.listarItensPorCompraId(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Produto Teste");
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
        List<ItemDTO> items = Collections.singletonList(itemDTO);
        when(itemRepository.findByNomeByPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        )).thenReturn(items);

        List<ItemDTO> resultado = itemService.listarItensPorNomeEPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findByNomeByPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        );
    }

    @Test
    void deveBuscarProdutosPaginados() {
        Page<ItemDTO> page = new PageImpl<>(Collections.singletonList(itemDTO));
        Pageable pageable = PageRequest.of(0, 10);
        when(itemRepository.findByNomeByPeriodoPaginado(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado",
                pageable
        )).thenReturn(page);

        Page<ItemDTO> resultado = itemService.buscarProdutosPaginados(
                "Produto",
                "Mercado",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                pageable
        );

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Produto Teste");
        verify(itemRepository).findByNomeByPeriodoPaginado(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado",
                pageable
        );
    }

    @Test
    void deveDeletarItemPorId() {
        itemService.deleteById(1L);
        verify(itemRepository).deleteById(1L);
    }

    @Test
    void deveDeletarItensPorCompraId() {
        itemService.deleteByCompraId(1L);
        verify(itemRepository).deleteByCompraId(1L);
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

        ItemDTO resultado = itemService.atualizarItem(itemParaAtualizar);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Produto Atualizado");
        verify(itemRepository).findById(1L);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deveLancarExcecaoQuandoAtualizarItemNaoEncontrado() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

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

        try {
            itemService.atualizarItem(itemParaAtualizar);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Item não encontrado");
        }

        verify(itemRepository).findById(1L);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void deveSalvarOuAtualizarItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        itemService.salvarOuAtualizar(item);

        verify(itemRepository).save(item);
    }
}