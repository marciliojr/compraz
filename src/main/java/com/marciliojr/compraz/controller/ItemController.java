package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import com.marciliojr.compraz.service.PDFGenerationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.marciliojr.compraz.infra.ComprazUtils.parseDate;
import static com.marciliojr.compraz.infra.ComprazUtils.sanitizeString;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    private  PDFGenerationService pdfGenerationService;

    @GetMapping("/itens")
    public ResponseEntity<List<ItemDTO>> buscarItensPorEstabelecimentoEPeriodo(
            @RequestParam(required = false) String nomeEstabelecimento,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        LocalDate inicio = parseDate(dataInicio);
        LocalDate fim = parseDate(dataFim);
        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, inicio, fim);

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/soma-valor-unitario")
    public ResponseEntity<BigDecimal> somarValorUnitarioPorEstabelecimentoEPeriodo(
            @RequestParam String nomeEstabelecimento,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        LocalDate inicio = (dataInicio != null && !dataInicio.isEmpty()) ? LocalDate.parse(dataInicio) : null;
        LocalDate fim = (dataFim != null && !dataFim.isEmpty()) ? LocalDate.parse(dataFim) : null;
        BigDecimal soma = itemService.somarValorUnitarioPorEstabelecimentoEPeriodo(StringUtils.trimToNull(nomeEstabelecimento), inicio, fim);

        return ResponseEntity.ok(soma);
    }

    @GetMapping("/exportar/pdf")
    public ResponseEntity<byte[]> exportarParaPDF(
            @RequestParam String nomeEstabelecimento,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        LocalDate inicio = (dataInicio != null && !dataInicio.isEmpty()) ? LocalDate.parse(dataInicio) : null;
        LocalDate fim = (dataFim != null && !dataFim.isEmpty()) ? LocalDate.parse(dataFim) : null;
        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, inicio, fim);

        try {
            byte[] pdfBytes = pdfGenerationService.generatePDF(itens);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_compras.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
