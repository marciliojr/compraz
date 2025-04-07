package com.marciliojr.compraz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.service.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping("/nomes")
    public ResponseEntity<List<Estabelecimento>> listarNomesEstabelecimento() {
        List<Estabelecimento> estabelecimentos = estabelecimentoService.listarTodos();
        return ResponseEntity.ok(estabelecimentos);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removerEstabelecimento(@PathVariable Long id) {
        try {
            estabelecimentoService.removerEstabelecimento(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


    @PutMapping("/atualiza")
    public ResponseEntity<Estabelecimento> atualizarEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        try {
            System.out.println("Raw body: " + new ObjectMapper().writeValueAsString(estabelecimento));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Estabelecimento atualizado = estabelecimentoService.salvarOuAtualizar(estabelecimento);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping
    public ResponseEntity<Estabelecimento> criarEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        Estabelecimento novoEstabelecimento = estabelecimentoService.salvarOuAtualizar(estabelecimento);
        return ResponseEntity.ok(novoEstabelecimento);
    }

}
