package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.NomesNormalizados;
import com.marciliojr.compraz.repository.NomesNormalizadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NomesNormalizadosService {

    private final NomesNormalizadosRepository nomesNormalizadosRepository;
    private final ItemService itemService;

    @Autowired
    public NomesNormalizadosService(NomesNormalizadosRepository nomesNormalizadosRepository, ItemService itemService) {
        this.nomesNormalizadosRepository = nomesNormalizadosRepository;
        this.itemService = itemService;
    }

    public void salvarNomeNormalizado(String abreviacaoMercado, String nomeRegularizado) {
        NomesNormalizados nomeNormalizado = new NomesNormalizados(abreviacaoMercado, nomeRegularizado);
        nomesNormalizadosRepository.save(nomeNormalizado);
    }

    public void salvarOuAtualizar(NomesNormalizados nomeNormalizado) {
        nomesNormalizadosRepository.save(nomeNormalizado);
    }

    public void removerNomeNormalizado(Long id) {
        nomesNormalizadosRepository.deleteById(id);
    }


    public List<NomesNormalizados> listarTodosNomesNormalizados() {
        return nomesNormalizadosRepository.findAll();
    }

    public NomesNormalizados buscarNomeNormalizadoPorId(Long id) {
        return nomesNormalizadosRepository.findById(id).orElse(null);
    }

    public void normalizarNomes() {
        Map<String, String> map = new HashMap<>();
        List<Item> items = itemService.listarTodos();
        List<NomesNormalizados> nomesNormalizados = listarTodosNomesNormalizados();

        for (NomesNormalizados nomeNormalizado : nomesNormalizados) {
            map.put(nomeNormalizado.getAbreviacaoMercado(), nomeNormalizado.getNomeRegularizado());
        }
        for (Item item : items) {
            String[] palavras = item.getNome().trim().split(" ");
            for (int i = 0; i < palavras.length; i++) {
                String chave = palavras[i].toUpperCase();
                if (map.containsKey(chave)) {
                    palavras[i] = map.get(chave);
                }
            }
            item.setNome(String.join(" ", palavras));
            itemService.salvarOuAtualizar(item);
        }
    }
}
