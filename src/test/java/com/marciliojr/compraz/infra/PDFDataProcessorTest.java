package com.marciliojr.compraz.infra;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PDFDataProcessorTest {

    @Test
    void deveProcessarPDFDataComSucesso() {
        String textoMock = "Produto Teste (Código: 123) Qtde.: 2 UN: kg Vl. Unit.: 10,50";

        ByteArrayOutputStream saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));

        PDFDataProcessor.processPDFData(textoMock);

        String resultado = saidaCapturada.toString().trim();
        assertTrue(resultado.contains("Produto: Produto Teste, Qtde: 2, UN: kg, Valor Unitário: 10,50"));
    }
}
