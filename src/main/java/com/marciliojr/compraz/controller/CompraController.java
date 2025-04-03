package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.model.dto.CompraRelatorioDTO;
import com.marciliojr.compraz.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/compra")
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping("/compras")
    public ResponseEntity<List<CompraDTO>> listarCompras(@RequestParam(required = false) String nomeEstabelecimento, @RequestParam Integer tipoCupom, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        TipoCupom tipoCupomEnum = TipoCupom.obterPorCodigo(tipoCupom);

        List<CompraDTO> compras = compraService.listarComprasPorEstabelecimentoEPeriodo(nomeEstabelecimento, tipoCupomEnum, dataInicio, dataFim);

        return ResponseEntity.ok(compras);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirCompraPorId(@RequestParam Long id) {
        compraService.excluirCompraPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<List<CompraRelatorioDTO>> gerarRelatorio(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        List<CompraRelatorioDTO> relatorio = compraService.gerarRelatorioPorDataCompra(dataInicio, dataFim);
        return ResponseEntity.ok(relatorio);
    }

}
