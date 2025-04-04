package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.service.EstabelecimentoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/remove")
    public ResponseEntity<Void> removerEstabelecimento(Long id) {
        estabelecimentoService.removerEstabelecimento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualiza")
    public ResponseEntity<Estabelecimento> atualizarEstabelecimento(Estabelecimento estabelecimento) {
        Estabelecimento atualizado = estabelecimentoService.salvarOuAtualizar(estabelecimento);
        return ResponseEntity.ok(atualizado);
    }

    @PostMapping
    public ResponseEntity<Estabelecimento> criarEstabelecimento(@RequestBody Estabelecimento estabelecimento) {
        Estabelecimento novoEstabelecimento = estabelecimentoService.salvarOuAtualizar(estabelecimento);
        return ResponseEntity.ok(novoEstabelecimento);
    }

}
