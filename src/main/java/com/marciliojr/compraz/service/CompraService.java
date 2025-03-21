package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public CompraService() {
    }

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<CompraDTO> listarComprasPorEstabelecimentoEPeriodo(String nomeEstabelecimento, TipoCupom tipoCupom, LocalDate dataInicio, LocalDate dataFim) {
        return compraRepository.findByNomeEstabelecimentoDataCompraValorTotal(nomeEstabelecimento, tipoCupom, dataInicio, dataFim);
    }

    public void excluirCompraPorId(Long id) {
        compraRepository.deleteById(id);
    }

    public CompraDTO buscarCompraPorEstabelecimentoEData(String nomeEstabelecimento, String dataCompra) {
        return compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(nomeEstabelecimento, dataCompra).orElse(null);
    }

}
