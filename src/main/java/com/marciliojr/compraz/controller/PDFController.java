package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.infra.PDFExtractor;
import com.marciliojr.compraz.service.PDFDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controlador REST para manipulação de arquivos PDF e processamento de dados extraídos.
 */
@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFDataService pdfDataService;

    private final PDFExtractor pdfExtractor = new PDFExtractor();

    /**
     * Endpoint para upload de arquivos PDF.
     *
     * @param file               O arquivo PDF enviado pelo cliente.
     * @param nomeEstabelecimento O nome do estabelecimento associado ao PDF.
     * @return Uma mensagem indicando o status do processamento.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDF(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nomeEstabelecimento") String nomeEstabelecimento) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Arquivo não enviado ou está vazio.");
        }

        if (nomeEstabelecimento == null || nomeEstabelecimento.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Nome do estabelecimento não fornecido.");
        }

        try {
            // Extrai texto do PDF e processa os dados
            String textoPDF = pdfExtractor.extrairTextoPDF(pdfExtractor.converterParaArquivo(file));
            pdfDataService.processarDadosEPersistir(textoPDF, nomeEstabelecimento);

            return ResponseEntity.status(HttpStatus.OK).body("Dados salvos com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o arquivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    /**
     * Endpoint para teste de conexão ou disponibilidade.
     *
     * @return Uma mensagem de teste.
     */
    @GetMapping("/teste")
    public ResponseEntity<String> testeConexao() {
        return ResponseEntity.status(HttpStatus.OK).body("API funcionando corretamente.");
    }
}
