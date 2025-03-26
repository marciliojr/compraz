package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.service.PDFDadosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PDFControllerTest {

    @Mock
    private PDFDadosService pdfDadosService;

    @InjectMocks
    private PDFController pdfController;

    private MockMultipartFile file;
    private String nomeEstabelecimento;
    private LocalDate dataCadastro;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "PDF content".getBytes()
        );
        nomeEstabelecimento = "Mercado Teste";
        dataCadastro = LocalDate.now();
    }

    @Test
    void deveRetornarErroQuandoArquivoEstiverVazio() {

        MockMultipartFile emptyFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                new byte[0]
        );


        ResponseEntity<String> response = pdfController.uploadPDF(
                emptyFile,
                nomeEstabelecimento,
                dataCadastro,
                1
        );


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Erro: Arquivo não enviado ou está vazio.");
    }

    @Test
    void deveRetornarErroQuandoNomeEstabelecimentoForVazio() {

        ResponseEntity<String> response = pdfController.uploadPDF(
                file,
                "",
                dataCadastro,
                1
        );


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Erro: Nome do estabelecimento não fornecido.");
    }
}