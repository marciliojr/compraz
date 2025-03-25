package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import com.marciliojr.compraz.service.PDFGeradorProdutos;
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
import java.util.Objects;

import static com.marciliojr.compraz.infra.ComprazUtils.sanitizeString;

@RestController
@RequestMapping("/api/cupom")
public class ItemController {

    private final ItemService itemService;
    private final PDFGeradorProdutos pdfGeradorProdutos;

    @Autowired
    public ItemController(ItemService itemService, PDFGeradorProdutos pdfGeradorProdutos) {
        this.itemService = itemService;
        this.pdfGeradorProdutos = pdfGeradorProdutos;
    }

    @GetMapping("/itens")
    public ResponseEntity<List<ItemDTO>> buscarItensPorEstabelecimentoEPeriodo(
            @RequestParam(required = false) String nomeEstabelecimento,
            @RequestParam(required = false) Integer tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim);

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ItemDTO>> buscarItensPorNomeProdutoEstabelecimentoTipoEPeriodo(
            @RequestParam(required = false) String nomeProduto,
            @RequestParam(required = false) String nomeEstabelecimento,
            @RequestParam(required = false) Integer tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        nomeProduto = sanitizeString(nomeProduto);
        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        List<ItemDTO> itens = itemService.listarItensPorNomeEPeriodo(nomeProduto, Objects.requireNonNullElse(TipoCupom.obterPorCodigo(tipoCupom), TipoCupom.OUTROS), dataInicio, dataFim, nomeEstabelecimento);

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/soma-valor-total")
    public ResponseEntity<BigDecimal> somarValorTotalPorEstabelecimentoEPeriodo(
            @RequestParam String nomeEstabelecimento,
            @RequestParam int tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        BigDecimal soma = itemService.somarValorTotalPorEstabelecimentoEPeriodo(StringUtils.trimToNull(nomeEstabelecimento), TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim);

        return ResponseEntity.ok(soma);
    }

    @GetMapping("/exportar/pdf")
    public ResponseEntity<byte[]> exportarParaPDF(
            @RequestParam String nomeEstabelecimento,
            @RequestParam int tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        try {
            List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim);

            BigDecimal valorSomatorio = itemService.somarValorTotalPorEstabelecimentoEPeriodo(nomeEstabelecimento, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim);

            byte[] pdfBytes = pdfGeradorProdutos.generatePDF(itens, Objects.requireNonNullElse(valorSomatorio.toString(), "0.00"));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_compras.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
