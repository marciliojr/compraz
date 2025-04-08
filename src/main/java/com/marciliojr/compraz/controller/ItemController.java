package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import com.marciliojr.compraz.service.PDFGeradorProdutos;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.marciliojr.compraz.infra.ComprazUtils.sanitizeString;

@RestController
@RequestMapping("/api/item")
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
            @RequestParam(required = true) Integer tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim);

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/itens/compra/{compraId}")
    public ResponseEntity<List<ItemDTO>> buscarItensPorCompraId(
            @PathVariable Long compraId) {
        List<ItemDTO> itens = itemService.listarItensPorCompraId(compraId);
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/produtos/todos")
    public ResponseEntity<List<ItemDTO>> listarTodos() {
        List<ItemDTO> itens = itemService.listarTodosItemDTO();
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

        List<ItemDTO> itens = itemService.listarItensPorNomeEPeriodo(nomeProduto, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim, nomeEstabelecimento);

        return ResponseEntity.ok(itens);
    }

    @GetMapping("/produtos/paginado")
    public ResponseEntity<Page<ItemDTO>> buscarProdutos(
            @RequestParam(required = false) String nomeProduto,
            @RequestParam(required = false) String nomeEstabelecimento,
            @RequestParam(required = false) Integer tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sort
    ) {
        String campoOrdenacao = "nome"; // Campo padrão
        if (sort != null) {
            switch (sort.toLowerCase()) {
                case "nome":
                case "quantidade":
                case "unidade":
                case "valortotal":
                case "valorunitario":
                case "datacompra":
                case "nomeestabelecimento":
                    campoOrdenacao = sort;
                    break;
                default:
                    // Se o campo não for válido, usa o padrão
                    break;
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(campoOrdenacao));

        Page<ItemDTO> produtos = itemService.buscarProdutosPaginados(
                nomeProduto,
                nomeEstabelecimento,
                TipoCupom.obterPorCodigo(tipoCupom),
                dataInicio,
                dataFim,
                pageable
        );

        return ResponseEntity.ok(produtos);
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

    @GetMapping("/exportar/produto/pdf")
    public ResponseEntity<byte[]> exportarProdutoParaPDF(
            @RequestParam String nomeProduto,
            @RequestParam String nomeEstabelecimento,
            @RequestParam int tipoCupom,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        nomeProduto = sanitizeString(nomeProduto);

        nomeEstabelecimento = sanitizeString(nomeEstabelecimento);

        try {

            List<ItemDTO> itens = itemService.listarItensPorNomeEPeriodo(nomeProduto, TipoCupom.obterPorCodigo(tipoCupom), dataInicio, dataFim, nomeEstabelecimento);

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

    @DeleteMapping("compras/excluir")
    public ResponseEntity<Void> excluirItemPorId(@RequestParam Long id) {
        itemService.deleteByCompraId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ItemDTO> atualizarItem(@RequestBody ItemDTO itemDTO) {
        ItemDTO itemAtualizado = itemService.atualizarItem(itemDTO);
        return ResponseEntity.ok(itemAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/compra/{compraId}")
    public ResponseEntity<Void> deletarItensPorCompra(@PathVariable Long compraId) {
        itemService.deleteByCompraId(compraId);
        return ResponseEntity.noContent().build();
    }
}
