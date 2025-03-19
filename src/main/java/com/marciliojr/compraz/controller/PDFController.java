package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.infra.PDFExtractor;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.service.PDFDadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    private final PDFExtractor pdfExtractor = new PDFExtractor();
    @Autowired
    private PDFDadosService pdfDadosService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> uploadPDF(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nomeEstabelecimento") String nomeEstabelecimento,
            @RequestParam("dataCadastro") LocalDate dataCadastro,
            @RequestParam("tipoCupom") int tipoCupom) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Arquivo não enviado ou está vazio.");
        }

        if (nomeEstabelecimento == null || nomeEstabelecimento.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: Nome do estabelecimento não fornecido.");
        }

        try {
            String textoPDF = pdfExtractor.extrairTextoPDF(pdfExtractor.converterParaArquivo(file));
            pdfDadosService.processarDadosEPersistir(textoPDF, nomeEstabelecimento, dataCadastro, TipoCupom.obterPorCodigo(tipoCupom));

            return ResponseEntity.status(HttpStatus.OK).body("Dados salvos com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o arquivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

}
