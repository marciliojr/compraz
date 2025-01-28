package com.marciliojr.compraz.infra;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PDFDataProcessorTest {

    @Test
    void processPDFData_DeveExtrairInformacoesCorretamente() {
        String textoPDF = "Arroz (Código: 12345) Qtde.: 2.00 UN: Kg Vl. Unit.: 10.00 Vl. Total 20.00";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        PDFDataProcessor.processPDFData(textoPDF);

        String output = outContent.toString();

        assertTrue(output.contains("Produto: Arroz"));
        assertTrue(output.contains("Qtde: 2.00"));
        assertTrue(output.contains("UN: Kg"));
        assertTrue(output.contains("Valor Unitário: 10.00"));

        System.setOut(System.out);
    }
}
