package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.NomesNormalizados;
import com.marciliojr.compraz.service.NomesNormalizadosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NomesNormalizadosControllerTest {

    @Mock
    private NomesNormalizadosService nomesNormalizadosService;

    @InjectMocks
    private NomesNormalizadosController nomesNormalizadosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarNomeNormalizado_DeveChamarService() {
        // Arrange
        String abreviacaoMercado = "TEST";
        String nomeRegularizado = "Teste";

        // Act
        nomesNormalizadosController.salvarNomeNormalizado(abreviacaoMercado, nomeRegularizado);

        // Assert
        verify(nomesNormalizadosService).salvarNomeNormalizado(abreviacaoMercado, nomeRegularizado);
    }

    @Test
    void removerNomeNormalizado_DeveChamarService() {
        // Arrange
        Long id = 1L;

        // Act
        nomesNormalizadosController.removerNomeNormalizado(id);

        // Assert
        verify(nomesNormalizadosService).removerNomeNormalizado(id);
    }

    @Test
    void listarTodosNomesNormalizados_DeveRetornarLista() {
        // Arrange
        List<NomesNormalizados> nomes = Arrays.asList(new NomesNormalizados(), new NomesNormalizados());
        when(nomesNormalizadosService.listarTodosNomesNormalizados()).thenReturn(nomes);

        // Act
        ResponseEntity<List<NomesNormalizados>> response = nomesNormalizadosController.listarTodosNomesNormalizados();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nomes, response.getBody());
        verify(nomesNormalizadosService).listarTodosNomesNormalizados();
    }

    @Test
    void buscarNomeNormalizadoPorId_DeveRetornarNomeNormalizado() {
        // Arrange
        Long id = 1L;
        NomesNormalizados nomeNormalizado = new NomesNormalizados();
        when(nomesNormalizadosService.buscarNomeNormalizadoPorId(id)).thenReturn(nomeNormalizado);

        // Act
        ResponseEntity<NomesNormalizados> response = nomesNormalizadosController.buscarNomeNormalizadoPorId(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(nomeNormalizado, response.getBody());
        verify(nomesNormalizadosService).buscarNomeNormalizadoPorId(id);
    }

    @Test
    void normalizarNome_DeveRetornarNoContent() {
        // Act
        ResponseEntity<String> response = nomesNormalizadosController.normalizarNome();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(nomesNormalizadosService).normalizarNomes();
    }

    @Test
    void atualizar_QuandoNomeExiste_DeveAtualizarERetornarNoContent() {
        // Arrange
        Long id = 1L;
        NomesNormalizados nomesNormalizados = new NomesNormalizados();
        nomesNormalizados.setAbreviacaoMercado("TEST");
        nomesNormalizados.setNomeRegularizado("Teste");

        NomesNormalizados nomeNormalizadoBanco = new NomesNormalizados();
        when(nomesNormalizadosService.buscarNomeNormalizadoPorId(id)).thenReturn(nomeNormalizadoBanco);
        doNothing().when(nomesNormalizadosService).salvarOuAtualizar(any());

        // Act
        ResponseEntity<String> response = nomesNormalizadosController.atualizar(id, nomesNormalizados);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(nomesNormalizadosService).buscarNomeNormalizadoPorId(id);
        verify(nomesNormalizadosService).salvarOuAtualizar(any());
    }

    @Test
    void atualizar_QuandoNomeNaoExiste_DeveRetornarErro() {
        // Arrange
        Long id = 1L;
        NomesNormalizados nomesNormalizados = new NomesNormalizados();
        when(nomesNormalizadosService.buscarNomeNormalizadoPorId(id)).thenReturn(null);

        // Act
        ResponseEntity<String> response = nomesNormalizadosController.atualizar(id, nomesNormalizados);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Não foi possível atualizar o nome normalizado com o ID: " + id, response.getBody());
        verify(nomesNormalizadosService).buscarNomeNormalizadoPorId(id);
        verify(nomesNormalizadosService, never()).salvarOuAtualizar(any());
    }

    @Test
    void atualizarBase_DeveRetornarNoContent() {
        // Act
        ResponseEntity<String> response = nomesNormalizadosController.atualizarBase();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(nomesNormalizadosService, atLeastOnce()).salvarNomeNormalizado(anyString(), anyString());
    }
} 