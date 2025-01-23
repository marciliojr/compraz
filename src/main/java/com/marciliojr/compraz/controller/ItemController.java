package com.marciliojr.compraz.controller;

import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.service.ItemService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/itens")
    public ResponseEntity<List<ItemDTO>> buscarItensPorEstabelecimentoEPeriodo(
            @RequestParam(required = false) String nomeEstabelecimento,
            @RequestParam(required = false) String dataInicio,
            @RequestParam(required = false) String dataFim) {

        LocalDate inicio = (dataInicio != null && !"null".equalsIgnoreCase(dataInicio)) ? LocalDate.parse(dataInicio) : null;
        LocalDate fim = (dataFim != null && !"null".equalsIgnoreCase(dataFim)) ? LocalDate.parse(dataFim) : null;
        nomeEstabelecimento = ("null".equalsIgnoreCase(nomeEstabelecimento) || nomeEstabelecimento.trim().isEmpty()) ? null : nomeEstabelecimento;

        List<ItemDTO> itens = itemService.listarItensPorEstabelecimentoEPeriodo(nomeEstabelecimento, inicio, fim);

        return ResponseEntity.ok(itens);
    }


}
