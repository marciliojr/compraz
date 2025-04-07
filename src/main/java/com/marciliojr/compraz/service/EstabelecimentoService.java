package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstabelecimentoService {

    EstabelecimentoRepository estabelecimentoRepository;

    CompraService compraService;

    public EstabelecimentoService() {
    }

    @Autowired
    EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository, CompraService compraService) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.compraService = compraService;
    }

    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    public void removerEstabelecimento(Long id) {
        if (compraService.existeComprasPorEstabelecimento(id)) {
            throw new RuntimeException("Existem compras relacionadas a este estabelecimento.");
        }
        try {
            estabelecimentoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover estabelecimento: " + e.getMessage());
        }
    }

    public Optional<Estabelecimento> obterEstabelecimento(String nome) {
        return estabelecimentoRepository.findByNomeEstabelecimento(nome);
    }

    public Estabelecimento salvarOuAtualizar(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }
}
