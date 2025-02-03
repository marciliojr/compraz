package com.marciliojr.compraz.infra;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class PDFExtractor {

    public String extrairTextoPDF(File pdfFile) {
        if (pdfFile == null || !pdfFile.exists()) {
            System.err.println("Erro: Arquivo PDF não encontrado ou inválido.");
            return null;
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            // Inicializa o objeto para extração de texto
            PDFTextStripper pdfStripper = new PDFTextStripper();

            // Extrai o texto do documento
            String text = pdfStripper.getText(document);

            // Retorna o texto extraído
            return text;
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo PDF: " + e.getMessage());
        }

        return null;
    }

    public File converterParaArquivo(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("O MultipartFile está vazio ou é nulo.");
        }

        // Cria um arquivo temporário no sistema
        File tempFile = File.createTempFile("upload_", "_" + multipartFile.getOriginalFilename());

        // Transfere o conteúdo do MultipartFile para o arquivo temporário
        multipartFile.transferTo(tempFile);

        // Retorna o arquivo temporário
        return tempFile;
    }
}
