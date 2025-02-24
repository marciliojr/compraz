package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;


    public ItemService() {
    }

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> listarTodos() {
        return itemRepository.findAll().stream().map(item -> ItemDTO.construir(item.getNome(), item.getQuantidade(), item.getUnidade(), item.getValorTotal(), item.getValorUnitario(), item.getCompra().getDataCompra(), item.getCompra().getEstabelecimento().getNomeEstabelecimento())).collect(Collectors.toList());
    }

    public List<ItemDTO> listarItensPorEstabelecimentoEPeriodo(String nomeEstabelecimento, LocalDate dataInicio, LocalDate dataFim) {

        if (Objects.isNull(dataInicio) && Objects.isNull(dataFim) && Strings.isBlank(nomeEstabelecimento)) {
            return listarTodos();
        }

        return itemRepository.findAllItemsByEstabelecimentoAndPeriodo(nomeEstabelecimento, dataInicio, dataFim);
    }

    public BigDecimal somarValorUnitarioPorEstabelecimentoEPeriodo(String nomeEstabelecimento, LocalDate dataInicio, LocalDate dataFim) {
        return itemRepository.sumValorTotalByEstabelecimentoAndPeriodo(nomeEstabelecimento, dataInicio, dataFim);
    }

    public List<ItemDTO> listarItensPorNomeEPeriodo(String nome, LocalDate dataInicio, LocalDate dataFim) {
        return itemRepository.findByNomeByPeriodo(nome, dataInicio, dataFim);
    }



}
