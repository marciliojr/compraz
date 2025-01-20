package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.service.PDFDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class b {

    @Mock
    private PDFDataService pdfDataService;

    @InjectMocks
    private PDFController pdfController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarErroSeArquivoVazio() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        ResponseEntity<String> resposta = pdfController.uploadPDF(file, "Mercado");

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Erro: Arquivo não enviado ou está vazio.", resposta.getBody());
    }

    @Test
    void deveRetornarErroSeNomeEstabelecimentoVazio() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);

        ResponseEntity<String> resposta = pdfController.uploadPDF(file, "");

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Erro: Nome do estabelecimento não fornecido.", resposta.getBody());
    }
}
