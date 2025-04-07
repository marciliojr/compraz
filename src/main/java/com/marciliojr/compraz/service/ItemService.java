package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<ItemDTO> listarTodosItemDTO() {
        return itemRepository.findAll().stream().map(item -> ItemDTO.construir(item.getNome(), item.getQuantidade(), item.getUnidade(), item.getValorTotal(), item.getValorUnitario(), item.getCompra().getDataCompra(), item.getCompra().getEstabelecimento().getNomeEstabelecimento())).collect(Collectors.toList());
    }

    List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public List<ItemDTO> listarItensPorEstabelecimentoEPeriodo(String nomeEstabelecimento, TipoCupom tipoCupom, LocalDate dataInicio, LocalDate dataFim) {
        if (Objects.isNull(dataInicio) && Objects.isNull(dataFim) && Strings.isBlank(nomeEstabelecimento) && tipoCupom == null) {
            return listarTodosItemDTO();
        }

        return itemRepository.findAllItemsByEstabelecimentoAndPeriodo(nomeEstabelecimento, tipoCupom, dataInicio, dataFim);
    }

    public List<ItemDTO> listarItensPorCompraId(Long compraId) {
        return itemRepository.findByCompraId(compraId);
    }

    public BigDecimal somarValorTotalPorEstabelecimentoEPeriodo(String nomeEstabelecimento, TipoCupom tipoCupom, LocalDate dataInicio, LocalDate dataFim) {
        return itemRepository.sumValorTotalByEstabelecimentoAndPeriodo(nomeEstabelecimento, tipoCupom, dataInicio, dataFim);
    }

    public List<ItemDTO> listarItensPorNomeEPeriodo(String nome, TipoCupom tipoCupom, LocalDate dataInicio, LocalDate dataFim, String nomeEstabelecimento) {
        return itemRepository.findByNomeByPeriodo(nome, tipoCupom, dataInicio, dataFim, nomeEstabelecimento);
    }

    public Page<ItemDTO> buscarProdutosPaginados(
            String nomeProduto,
            String nomeEstabelecimento,
            TipoCupom tipoCupom,
            LocalDate dataInicio,
            LocalDate dataFim,
            Pageable pageable
    ) {
        return itemRepository.findByNomeByPeriodoPaginado(nomeProduto, tipoCupom, dataInicio, dataFim, nomeEstabelecimento, pageable);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    public void deleteByCompraId(Long compraId) {
        itemRepository.deleteByCompraId(compraId);
    }

    public void atualizarItem(ItemDTO itemDTO) {
        Item item = itemRepository.findById(itemDTO.getId()).orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setNome(itemDTO.getNome());
        item.setQuantidade(itemDTO.getQuantidade());
        item.setUnidade(itemDTO.getUnidade());
        item.setValorUnitario(itemDTO.getValorUnitario());
        item.setValorTotal(itemDTO.getValorTotal());

        itemRepository.save(item);
    }

    public void salvarOuAtualizar(Item item) {
        itemRepository.save(item);
    }


}
