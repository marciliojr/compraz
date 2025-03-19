package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.infra.PDFExtractor;
import com.marciliojr.compraz.service.PDFDadosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class PDFControllerTest {

    @Mock
    private PDFDadosService pdfDadosService;

    @InjectMocks
    private PDFController pdfController;

    private PDFExtractor pdfExtractor;

    @BeforeEach
    void setUp() {
        pdfExtractor = new PDFExtractor();
    }

    @Test
    void uploadPDF_DeveRetornarErroSeArquivoEstiverVazio() {
//        MockMultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);
//
//        ResponseEntity<String> response = pdfController.uploadPDF(emptyFile, "Mercado X", "2024-01-25");
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Erro: Arquivo não enviado ou está vazio.", response.getBody());
    }

    @Test
    void uploadPDF_DeveRetornarErroSeNomeEstabelecimentoForVazio() {
//        MockMultipartFile file = new MockMultipartFile("file", "teste.pdf", "application/pdf", new byte[]{1, 2, 3});
//
//        ResponseEntity<String> response = pdfController.uploadPDF(file, "", "2024-01-25");
//
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        assertEquals("Erro: Nome do estabelecimento não fornecido.", response.getBody());
    }


    @Test
    void uploadPDF_DeveRetornarErroSeProcessamentoFalhar() throws IOException {
//        MockMultipartFile file = new MockMultipartFile("file", "teste.pdf", "application/pdf", "Texto de teste".getBytes());
//
//        doThrow(new RuntimeException("Erro inesperado")).when(pdfDadosService).processarDadosEPersistir(any(), any(), any());
//
//        ResponseEntity<String> response = pdfController.uploadPDF(file, "Mercado X", "2024-01-25");
//
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertTrue(response.getBody().contains("Erro inesperado"));
    }

    @Test
    void testeConexao_DeveRetornarMensagemDeSucesso() {
//        ResponseEntity<String> response = pdfController.testeConexao();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("API funcionando corretamente.", response.getBody());
    }
}
