package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.RelatorioItemDTO;
import com.marciliojr.compraz.model.dto.TopProdutosDTO;
import com.marciliojr.compraz.model.dto.RelatorioComparativoDTO;
import com.marciliojr.compraz.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Autowired
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/itens-agrupados")
    public ResponseEntity<List<RelatorioItemDTO>> gerarRelatorioItensAgrupados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<RelatorioItemDTO> relatorio = relatorioService.gerarRelatorioItensAgrupados(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

    /**
     * Endpoint para gráfico de barras horizontais dos produtos mais comprados
     * @param dataInicio Data inicial do período (obrigatório)
     * @param dataFim Data final do período (obrigatório)
     * @param limite Número máximo de produtos a retornar (opcional, padrão: 10)
     * @return Lista dos top produtos ordenados por quantidade total
     */
    @GetMapping("/top-produtos")
    public ResponseEntity<List<TopProdutosDTO>> gerarTopProdutosMaisComprados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false, defaultValue = "30") Integer limite) {
        List<TopProdutosDTO> topProdutos = relatorioService.gerarTopProdutosMaisComprados(dataInicio, dataFim, limite);
        return ResponseEntity.ok(topProdutos);
    }

    /**
     * Endpoint para relatório comparativo de preços de produtos
     * Compara preços de todos os produtos entre estabelecimentos no período especificado vs período anterior
     * @param dataInicio Data inicial do período (obrigatório)
     * @param dataFim Data final do período (obrigatório)
     * @return Lista de relatórios comparativos para todos os produtos do período
     */
    @GetMapping("/comparativo-precos")
    public ResponseEntity<List<RelatorioComparativoDTO>> gerarRelatorioComparativoPrecos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<RelatorioComparativoDTO> relatorio = relatorioService.gerarRelatorioComparativoPrecos(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }
} 