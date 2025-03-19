package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.dto.ItemDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class PDFGeradorProdutosTest {

    private PDFGeradorProdutos pdfGeradorProdutos;
    private List<ItemDTO> mockItems;

    @BeforeEach
    void setUp() {
//        pdfGeradorProdutos = new PDFGeradorProdutos();
//        mockItems = Arrays.asList(
//                new ItemDTO(1L, "Arroz", BigDecimal.valueOf(5), "Kg", BigDecimal.valueOf(10), BigDecimal.valueOf(50), LocalDate.of(2024, 1, 20), "Mercado X"),
//                new ItemDTO(2L, "Feijão", BigDecimal.valueOf(2), "Kg", BigDecimal.valueOf(8), BigDecimal.valueOf(16), LocalDate.of(2024, 1, 21), "Mercado Y")
//        );
    }

    @Test
    void generatePDF_DeveGerarPDFComDadosCorretos() throws IOException {
//        byte[] pdfBytes = pdfGeradorProdutos.generatePDF(mockItems);
//
//        assertNotNull(pdfBytes);
//        assertTrue(pdfBytes.length > 0);
//
//        // Convertendo bytes para PDF
//        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
//            PDFTextStripper pdfStripper = new PDFTextStripper();
//            String text = pdfStripper.getText(document);
//
//            // Verificações de conteúdo
//            assertTrue(text.contains("Relatório de Compras"));
//            assertTrue(text.contains("Arroz"));
//            assertTrue(text.contains("Feijão"));
//            assertTrue(text.contains("Mercado X"));
//            assertTrue(text.contains("Mercado Y"));
//            assertTrue(text.contains("10"));
//            assertTrue(text.contains("50"));
//            assertTrue(text.contains("8"));
//            assertTrue(text.contains("16"));
    }
}