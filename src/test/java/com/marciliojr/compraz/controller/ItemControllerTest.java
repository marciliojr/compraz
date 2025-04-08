package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import com.marciliojr.compraz.service.PDFGeradorProdutos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private PDFGeradorProdutos pdfGeradorProdutos;

    @InjectMocks
    private ItemController itemController;

    private ItemDTO itemDTO;
    private LocalDate dataCompra;

    @BeforeEach
    void setUp() {
        dataCompra = LocalDate.now();
        itemDTO = new ItemDTO(
                1L,
                "Produto Teste",
                BigDecimal.ONE,
                "UN",
                BigDecimal.TEN,
                BigDecimal.TEN,
                dataCompra,
                "Mercado Teste"
        );
    }

    @Test
    void deveBuscarItensPorEstabelecimentoEPeriodo() {
        List<ItemDTO> itens = Collections.singletonList(itemDTO);
        when(itemService.listarItensPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra
        )).thenReturn(itens);

        ResponseEntity<List<ItemDTO>> response = itemController.buscarItensPorEstabelecimentoEPeriodo(
                "Mercado Teste",
                1,
                dataCompra,
                dataCompra
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        ItemDTO resultadoDTO = response.getBody().get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(resultadoDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
    }

    @Test
    void deveBuscarItensPorNomeProdutoEstabelecimentoTipoEPeriodo() {

        List<ItemDTO> itens = Collections.singletonList(itemDTO);
        when(itemService.listarItensPorNomeEPeriodo(
                "Produto",
                TipoCupom.MERCADO,
                dataCompra,
                dataCompra,
                "Mercado"
        )).thenReturn(itens);


        ResponseEntity<List<ItemDTO>> response = itemController.buscarItensPorNomeProdutoEstabelecimentoTipoEPeriodo(
                "Produto",
                "Mercado",
                1,
                dataCompra,
                dataCompra
        );


        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        ItemDTO resultadoDTO = response.getBody().get(0);
        assertThat(resultadoDTO.getNome()).isEqualTo("Produto Teste");
        assertThat(resultadoDTO.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
    }

    @Test
    void deveAtualizarItem() {
        when(itemService.atualizarItem(any(ItemDTO.class))).thenReturn(itemDTO);

        ResponseEntity<ItemDTO> response = itemController.atualizarItem(itemDTO);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Produto Teste");
        verify(itemService).atualizarItem(any(ItemDTO.class));
    }

    @Test
    void deveDeletarItem() {
        ResponseEntity<Void> response = itemController.deletarItem(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(itemService).deleteById(1L);
    }

    @Test
    void deveDeletarItensPorCompra() {
        ResponseEntity<Void> response = itemController.deletarItensPorCompra(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(itemService).deleteByCompraId(1L);
    }
}