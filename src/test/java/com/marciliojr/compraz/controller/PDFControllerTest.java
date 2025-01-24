package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.service.PDFDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PDFControllerTest {

    @Mock
    private PDFDataService pdfDataService;

    @InjectMocks
    private PDFController pdfController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled
    void deveRetornarErroSeArquivoVazio() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        ResponseEntity<String> resposta = pdfController.uploadPDF(file, "Mercado", LocalDate.now().format(DateTimeFormatter.ISO_DATE_TIME));

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Erro: Arquivo não enviado ou está vazio.", resposta.getBody());
    }

    @Test
    @Disabled
    void deveRetornarErroSeNomeEstabelecimentoVazio() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);

        ResponseEntity<String> resposta = pdfController.uploadPDF(file, "", LocalDate.now().format(DateTimeFormatter.ISO_DATE_TIME));

        assertEquals(400, resposta.getStatusCodeValue());
        assertEquals("Erro: Nome do estabelecimento não fornecido.", resposta.getBody());
    }
}
