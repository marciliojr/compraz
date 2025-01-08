package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador REST responsável por expor endpoints relacionados aos itens.
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    /**
     * Construtor para injeção de dependência do serviço de itens.
     *
     * @param itemService Serviço responsável pelas operações relacionadas aos itens.
     */
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Endpoint para buscar itens com base no nome do estabelecimento e na data de compra.
     *
     * @param nomeEstabelecimento O nome do estabelecimento onde os itens foram comprados.
     * @param dataCompra          A data da compra no formato ISO (ex.: "2025-01-01").
     * @return Uma lista de objetos {@link ItemDTO} representando os itens encontrados.
     *         Retorna uma resposta HTTP 400 se houver erro nos parâmetros de entrada.
     */
    @GetMapping("/itens")
    public ResponseEntity<List<ItemDTO>> buscarItensPorEstabelecimentoEData(
            @RequestParam String nomeEstabelecimento,
            @RequestParam String dataCompra) {
        try {
            LocalDate data = LocalDate.parse(dataCompra);

            List<ItemDTO> itens = itemService.listarItensPorDataEstabelecimento(nomeEstabelecimento, data);

            return ResponseEntity.ok(itens);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(null);
        }
    }
}
