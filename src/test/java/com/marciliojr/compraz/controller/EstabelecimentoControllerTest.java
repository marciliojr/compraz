package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.service.EstabelecimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstabelecimentoControllerTest {

    @Mock
    private EstabelecimentoService estabelecimentoService;

    @InjectMocks
    private EstabelecimentoController estabelecimentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarNomesEstabelecimento_DeveRetornarListaDeEstabelecimentos() {
        // Arrange
        List<Estabelecimento> estabelecimentos = Arrays.asList(
            new Estabelecimento(), new Estabelecimento()
        );
        when(estabelecimentoService.listarTodos()).thenReturn(estabelecimentos);

        // Act
        ResponseEntity<List<Estabelecimento>> response = estabelecimentoController.listarNomesEstabelecimento();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estabelecimentos, response.getBody());
        verify(estabelecimentoService).listarTodos();
    }

    @Test
    void removerEstabelecimento_QuandoSucesso_DeveRetornarNoContent() {
        // Arrange
        Long id = 1L;
        doNothing().when(estabelecimentoService).removerEstabelecimento(id);

        // Act
        ResponseEntity<String> response = estabelecimentoController.removerEstabelecimento(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(estabelecimentoService).removerEstabelecimento(id);
    }

    @Test
    void removerEstabelecimento_QuandoErro_DeveRetornarInternalServerError() {
        // Arrange
        Long id = 1L;
        String mensagemErro = "Erro ao remover estabelecimento";
        doThrow(new RuntimeException(mensagemErro)).when(estabelecimentoService).removerEstabelecimento(id);

        // Act
        ResponseEntity<String> response = estabelecimentoController.removerEstabelecimento(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody());
    }

    @Test
    void atualizarEstabelecimento_DeveRetornarEstabelecimentoAtualizado() {
        // Arrange
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoService.salvarOuAtualizar(estabelecimento)).thenReturn(estabelecimento);

        // Act
        ResponseEntity<Estabelecimento> response = estabelecimentoController.atualizarEstabelecimento(estabelecimento);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estabelecimento, response.getBody());
        verify(estabelecimentoService).salvarOuAtualizar(estabelecimento);
    }

    @Test
    void criarEstabelecimento_DeveRetornarNovoEstabelecimento() {
        // Arrange
        Estabelecimento estabelecimento = new Estabelecimento();
        when(estabelecimentoService.salvarOuAtualizar(estabelecimento)).thenReturn(estabelecimento);

        // Act
        ResponseEntity<Estabelecimento> response = estabelecimentoController.criarEstabelecimento(estabelecimento);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(estabelecimento, response.getBody());
        verify(estabelecimentoService).salvarOuAtualizar(estabelecimento);
    }
} 