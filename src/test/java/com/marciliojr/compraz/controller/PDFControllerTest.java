package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.infra.PDFExtractor;
import com.marciliojr.compraz.model.TipoCupom;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PDFControllerTest {

    @Mock
    private PDFExtractor pdfExtractor;

    @Mock
    private PDFDadosService pdfDadosService;

    @InjectMocks
    private PDFController pdfController;

    private MockMultipartFile file;
    private String nomeEstabelecimento;
    private LocalDate dataCadastro;

    @BeforeEach
    void setUp() {
        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "PDF content".getBytes());
        nomeEstabelecimento = "Mercado Teste";
        dataCadastro = LocalDate.now();
    }

    @Test
    @Disabled
    void deveRetornarSucessoQuandoUploadForValido() throws IOException {
        // Simula extração do texto PDF
        String textoExtraido = "Texto simulado do PDF";
        File fakeFile = new File("fake.pdf");

        doReturn(fakeFile).when(pdfExtractor).converterParaArquivo(any(MultipartFile.class));
        doReturn(textoExtraido).when(pdfExtractor).extrairTextoPDF(any(File.class));

        doNothing().when(pdfDadosService).processarDadosEPersistir(eq(textoExtraido), eq(nomeEstabelecimento), eq(dataCadastro), eq(TipoCupom.MERCADO));

        ResponseEntity<String> response = pdfController.uploadPDF(file, nomeEstabelecimento, dataCadastro, 1 // tipoCupom = MERCADO
        );

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Dados salvos com sucesso!");

        verify(pdfDadosService).processarDadosEPersistir(eq(textoExtraido), eq(nomeEstabelecimento), eq(dataCadastro), eq(TipoCupom.MERCADO));
    }

    @Test
    void deveRetornarErroQuandoArquivoEstiverVazio() {

        MockMultipartFile emptyFile = new MockMultipartFile("file", "test.pdf", "application/pdf", new byte[0]);

        ResponseEntity<String> response = pdfController.uploadPDF(emptyFile, nomeEstabelecimento, dataCadastro, 1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Erro: Arquivo não enviado ou está vazio.");
    }

    @Test
    void deveRetornarErroQuandoNomeEstabelecimentoForVazio() {

        ResponseEntity<String> response = pdfController.uploadPDF(file, "", dataCadastro, 1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Erro: Nome do estabelecimento não fornecido.");
    }
}