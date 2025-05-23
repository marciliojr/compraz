package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Service
public class PDFDadosService {
    private static final String ITEM_REGEX = "(.*?)\\s*\\(Código:\\s*\\d+\\)\\s*Qtde\\.:\\s*(\\d+[,.]?\\d*)\\s*UN:\\s*(\\w+)\\s*Vl\\. Unit\\.:\\s*(\\d+[,.]?\\d+)\\s*Vl\\. Total\\s*(\\d+[,.]?\\d+)";
    private static final Logger logger = LoggerFactory.getLogger(PDFDadosService.class);
    private static final Pattern ITEM_PATTERN = Pattern.compile(ITEM_REGEX);

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    public void processarDadosEPersistir(String textoPDF, String nomeEstabelecimento, LocalDate dataCadastro, TipoCupom tipoCupom) {
        validarEntradas(textoPDF, nomeEstabelecimento);

        Estabelecimento estabelecimento = salvarEstabelecimento(nomeEstabelecimento, tipoCupom);
        LocalDate dataFormatada = isNull(dataCadastro) ? LocalDate.now() : dataCadastro;
        Optional<CompraDTO> compraEncontrada = compraRepository.findOneCompraDTOByNomeEstabelecimentoAndDataCompra(nomeEstabelecimento, dataFormatada);
        Compra compra = compraEncontrada.map(dto -> compraRepository.findById(dto.getId()).get())
                .orElseGet(() -> criarCompra(estabelecimento, dataCadastro));

        List<Item> itens = extrairItensDoTexto(textoPDF, compra);

        if (!itens.isEmpty()) {
            itens.forEach(item -> item.setCompra(compra));
            compra.setItens(itens);
            compraRepository.save(compra);
            logger.info("Dados processados e salvos com sucesso.");
        } else {
            logger.warn("Nenhum item válido encontrado no texto do PDF.");
        }
    }

    private void validarEntradas(String textoPDF, String nomeEstabelecimento) {
        if (isTextoVazioOuNulo(textoPDF, "Erro: O texto do PDF está vazio ou nulo.") || isTextoVazioOuNulo(nomeEstabelecimento, "Erro: O nome do estabelecimento está vazio ou nulo.")) {
            throw new IllegalArgumentException("Texto do PDF e nome do estabelecimento não podem ser vazios ou nulos.");
        }
    }

    private Estabelecimento salvarEstabelecimento(String nome, TipoCupom tipoCupom) {
        return estabelecimentoRepository.findByNomeEstabelecimento(nome).orElseGet(() -> {
            Estabelecimento novoEstabelecimento = new Estabelecimento();
            novoEstabelecimento.setTipoCupom(Objects.requireNonNullElse(tipoCupom, TipoCupom.OUTROS));
            novoEstabelecimento.setNomeEstabelecimento(nome);
            return estabelecimentoRepository.save(novoEstabelecimento);
        });
    }

    private Compra criarCompra(Estabelecimento estabelecimento, LocalDate dataCadastro) {
        Compra compra = new Compra();
        compra.setDataCompra(isNull(dataCadastro) ? LocalDate.now() : dataCadastro);
        compra.setEstabelecimento(estabelecimento);
        return compra;
    }

    private List<Item> extrairItensDoTexto(String textoPDF, Compra compra) {
        Matcher matcher = ITEM_PATTERN.matcher(textoPDF);
        List<Item> itens = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        while (matcher.find()) {
            try {
                Item item = criarItemAPartirDoMatcher(matcher, nf, compra);
                itens.add(item);
                logger.info("Item processado: {}", item);
            } catch (ParseException e) {
                logger.error("Erro ao processar item: {}", matcher.group(), e);
            }
        }

        return itens;
    }

    private Item criarItemAPartirDoMatcher(Matcher matcher, NumberFormat nf, Compra compra) throws ParseException {
        Item item = new Item();
        item.setNome(matcher.group(1).trim());
        item.setQuantidade(new BigDecimal(nf.parse(matcher.group(2)).toString()));
        item.setUnidade(matcher.group(3).trim());
        item.setValorUnitario(new BigDecimal(nf.parse(matcher.group(4)).toString()));
        item.setValorTotal(new BigDecimal(nf.parse(matcher.group(5)).toString()));
        item.setCompra(compra);
        return item;
    }

    private boolean isTextoVazioOuNulo(String texto, String mensagem) {
        if (texto == null || texto.trim().isEmpty()) {
            logger.error(mensagem);
            return true;
        }
        return false;
    }
}
