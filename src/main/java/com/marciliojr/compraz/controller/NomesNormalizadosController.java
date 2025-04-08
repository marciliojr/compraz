package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.infra.ItemNormalizer;
import com.marciliojr.compraz.model.NomesNormalizados;
import com.marciliojr.compraz.service.NomesNormalizadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nomes-normalizados")
public class NomesNormalizadosController {

    private final NomesNormalizadosService nomesNormalizadosService;

    @Autowired
    public NomesNormalizadosController(NomesNormalizadosService nomesNormalizadosService) {
        this.nomesNormalizadosService = nomesNormalizadosService;
    }

    @PostMapping
    public void salvarNomeNormalizado(@RequestParam String abreviacaoMercado, @RequestParam String nomeRegularizado) {
        nomesNormalizadosService.salvarNomeNormalizado(abreviacaoMercado, nomeRegularizado);
    }

    @DeleteMapping("/remover/{id}")
    public void removerNomeNormalizado(@PathVariable Long id) {
        nomesNormalizadosService.removerNomeNormalizado(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<NomesNormalizados>> listarTodosNomesNormalizados() {
        return ResponseEntity.ok(nomesNormalizadosService.listarTodosNomesNormalizados());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<NomesNormalizados> buscarNomeNormalizadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(nomesNormalizadosService.buscarNomeNormalizadoPorId(id));
    }

    @PostMapping("/normalizar")
    public ResponseEntity<String> normalizarNome() {
        nomesNormalizadosService.normalizarNomes();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody NomesNormalizados nomesNormalizados) {
        NomesNormalizados nomeNormalizadoBancoDeDados = nomesNormalizadosService.buscarNomeNormalizadoPorId(id);
        if (nomeNormalizadoBancoDeDados != null) {
            nomeNormalizadoBancoDeDados.setAbreviacaoMercado(nomesNormalizados.getAbreviacaoMercado());
            nomeNormalizadoBancoDeDados.setNomeRegularizado(nomesNormalizados.getNomeRegularizado());
            nomesNormalizadosService.salvarOuAtualizar(nomeNormalizadoBancoDeDados);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Não foi possível atualizar o nome normalizado com o ID: " + id);
        }
    }

    @PostMapping("/alimentarBase")
    public ResponseEntity<String> atualizarBase() {
        Map<String, String> mapaabreviacoes = ItemNormalizer.getMapaabreviacoes();
        for (Map.Entry<String, String> entry : mapaabreviacoes.entrySet()) {
            NomesNormalizados nomeNormalizado = new NomesNormalizados();
            nomeNormalizado.setAbreviacaoMercado(entry.getKey());
            nomeNormalizado.setNomeRegularizado(entry.getValue());
            nomesNormalizadosService.salvarNomeNormalizado(nomeNormalizado.getAbreviacaoMercado(), nomeNormalizado.getNomeRegularizado());
        }
        return ResponseEntity.noContent().build();
    }

}
