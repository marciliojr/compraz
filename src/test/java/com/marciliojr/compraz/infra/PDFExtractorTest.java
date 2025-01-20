package com.marciliojr.compraz.infra;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PDFExtractorTest {

    private PDFExtractor pdfExtractor;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pdfExtractor = new PDFExtractor();
    }

    @Test
    void deveExtrairTextoDePDF() throws IOException {
        File tempPdf = File.createTempFile("test", ".pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Texto de teste no PDF");
            contentStream.endText();
            contentStream.close();

            document.save(tempPdf);
        }

        // Executa a extração de texto
        String textoExtraido = pdfExtractor.extrairTextoPDF(tempPdf);

        // O texto extraído deve conter "Texto de teste no PDF"
        assertNotNull(textoExtraido);
        assertTrue(textoExtraido.contains("Texto de teste no PDF"));

        // Limpeza
        tempPdf.delete();
    }


    @Test
    void deveRetornarErroSeArquivoNuloOuInexistente() {
        assertNull(pdfExtractor.extrairTextoPDF(null));
    }

    @Test
    void deveConverterMultipartFileParaFile() throws IOException {
        // Criando um arquivo temporário com conteúdo fictício
        File tempFile = File.createTempFile("mockfile", ".pdf");
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Conteúdo de teste");
        writer.close();

        // Configuração do mock do MultipartFile
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("arquivo.pdf");

        doAnswer(invocation -> {
            File destino = invocation.getArgument(0);

            // Copia manualmente o conteúdo do arquivo temporário para o destino
            try (FileWriter destWriter = new FileWriter(destino)) {
                destWriter.write("Conteúdo de teste");
            }

            return null;
        }).when(multipartFile).transferTo(any(File.class));

        // Chama o método que queremos testar
        File resultado = pdfExtractor.converterParaArquivo(multipartFile);

        // Verificações
        assertNotNull(resultado, "O arquivo convertido não deve ser nulo");
        assertTrue(resultado.exists(), "O arquivo convertido deve existir");
        assertTrue(resultado.length() > 0, "O arquivo convertido deve conter dados");

        // Limpeza dos arquivos temporários
        tempFile.delete();
        resultado.delete();
    }


    @Test
    void deveLancarExcecaoSeMultipartFileForVazio() {
        when(multipartFile.isEmpty()).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> pdfExtractor.converterParaArquivo(multipartFile));
        assertEquals("O MultipartFile está vazio ou é nulo.", exception.getMessage());
    }
}
