package com.marciliojr.compraz.infra;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PDFExtractorTest {

    private PDFExtractor pdfExtractor;

    @BeforeEach
    void setUp() {
        pdfExtractor = new PDFExtractor();
    }

    @Test
    void extrairTextoPDF_DeveRetornarTextoCorreto() throws IOException {
        // Criando um PDF de teste na memória
        String textoEsperado = "Texto de teste no PDF";
        File pdfTeste = File.createTempFile("test_pdf", ".pdf");

        try (PDDocument documento = new PDDocument()) {
            PDPage page = new PDPage();
            documento.addPage(page);

            // Adicionando texto ao PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(documento, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(textoEsperado);
                contentStream.endText();
            }

            // Salvando o PDF no arquivo temporário
            documento.save(pdfTeste);
        }

        // Lendo o texto do PDF gerado
        String textoExtraido = pdfExtractor.extrairTextoPDF(pdfTeste);

        assertNotNull(textoExtraido);
        assertTrue(textoExtraido.contains(textoEsperado), "O texto extraído deve conter o texto esperado.");

        // Limpando arquivo temporário
        assertTrue(pdfTeste.delete());
    }
}
