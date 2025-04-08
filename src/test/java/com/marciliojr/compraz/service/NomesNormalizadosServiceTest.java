package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.NomesNormalizados;
import com.marciliojr.compraz.repository.NomesNormalizadosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NomesNormalizadosServiceTest {

    @Mock
    private NomesNormalizadosRepository nomesNormalizadosRepository;

    @Mock
    private ItemService itemService;

    @InjectMocks
    private NomesNormalizadosService nomesNormalizadosService;

    private NomesNormalizados nomeNormalizado;
    private Item item;

    @BeforeEach
    void setUp() {
        nomeNormalizado = new NomesNormalizados("AZEIT", "AZEITE");
        nomeNormalizado.setId(1L);

        item = new Item();
        item.setId(1L);
        item.setNome("AZEIT DE OLIVA");
    }

    @Test
    void deveSalvarNomeNormalizado() {
        nomesNormalizadosService.salvarNomeNormalizado("AZEIT", "AZEITE");
        verify(nomesNormalizadosRepository).save(any(NomesNormalizados.class));
    }

    @Test
    void deveSalvarOuAtualizarNomeNormalizado() {
        nomesNormalizadosService.salvarOuAtualizar(nomeNormalizado);
        verify(nomesNormalizadosRepository).save(nomeNormalizado);
    }

    @Test
    void deveRemoverNomeNormalizado() {
        nomesNormalizadosService.removerNomeNormalizado(1L);
        verify(nomesNormalizadosRepository).deleteById(1L);
    }

    @Test
    void deveListarTodosNomesNormalizados() {
        List<NomesNormalizados> nomesNormalizados = Arrays.asList(nomeNormalizado);
        when(nomesNormalizadosRepository.findAll()).thenReturn(nomesNormalizados);

        List<NomesNormalizados> resultado = nomesNormalizadosService.listarTodosNomesNormalizados();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAbreviacaoMercado()).isEqualTo("AZEIT");
        verify(nomesNormalizadosRepository).findAll();
    }

    @Test
    void deveBuscarNomeNormalizadoPorId() {
        when(nomesNormalizadosRepository.findById(1L)).thenReturn(Optional.of(nomeNormalizado));

        NomesNormalizados resultado = nomesNormalizadosService.buscarNomeNormalizadoPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getAbreviacaoMercado()).isEqualTo("AZEIT");
        verify(nomesNormalizadosRepository).findById(1L);
    }

    @Test
    void deveRetornarNullQuandoBuscarNomeNormalizadoPorIdNaoEncontrado() {
        when(nomesNormalizadosRepository.findById(1L)).thenReturn(Optional.empty());

        NomesNormalizados resultado = nomesNormalizadosService.buscarNomeNormalizadoPorId(1L);

        assertThat(resultado).isNull();
        verify(nomesNormalizadosRepository).findById(1L);
    }

    @Test
    void deveNormalizarNomes() {
        List<Item> items = Arrays.asList(item);
        List<NomesNormalizados> nomesNormalizados = Arrays.asList(nomeNormalizado);

        when(itemService.listarTodos()).thenReturn(items);
        when(nomesNormalizadosRepository.findAll()).thenReturn(nomesNormalizados);

        nomesNormalizadosService.normalizarNomes();

        verify(itemService).listarTodos();
        verify(nomesNormalizadosRepository).findAll();
        verify(itemService).salvarOuAtualizar(any(Item.class));
    }
} 