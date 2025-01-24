package com.marciliojr.compraz.service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFGenerationService {

    public byte[] generatePDF(List<ItemDTO> items) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            // Adiciona o título
            document.add(new Paragraph("Relatório de Compras")
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(ColorConstants.BLACK));

            // Define a tabela com o número de colunas
            float[] columnWidths = {3, 2, 2, 3,2};
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));

            // Cabeçalhos da tabela
            table.addHeaderCell(new Cell().add(new Paragraph("Nome do Item")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Quantidade")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Unidade")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Valor Unitário")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Estabelecimento")).setBold());

            // Adiciona os dados dos itens
            for (ItemDTO item : items) {
                table.addCell(new Cell().add(new Paragraph(item.getNome())));
                table.addCell(new Cell().add(new Paragraph(item.getQuantidade().toString())));
                table.addCell(new Cell().add(new Paragraph(item.getUnidade())));
                table.addCell(new Cell().add(new Paragraph("R$ " + item.getValorUnitario())));
                table.addCell(new Cell().add(new Paragraph(item.getNomeEstabelecimento())));
            }

            // Adiciona a tabela ao documento
            document.add(table);
            document.close();

            return outputStream.toByteArray();
        }
    }
}
