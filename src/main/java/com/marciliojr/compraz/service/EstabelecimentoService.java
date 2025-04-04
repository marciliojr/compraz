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

    public EstabelecimentoService() {
    }

    @Autowired
    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    public void removerEstabelecimento(Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    public Optional<Estabelecimento> obterEstabelecimento(String nome) {
        return estabelecimentoRepository.findByNomeEstabelecimento(nome);
    }

    public Estabelecimento salvarOuAtualizar(Estabelecimento estabelecimento) {
        return estabelecimentoRepository.save(estabelecimento);
    }
}
