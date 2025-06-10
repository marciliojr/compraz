package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.RelatorioItemDTO;
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
} 