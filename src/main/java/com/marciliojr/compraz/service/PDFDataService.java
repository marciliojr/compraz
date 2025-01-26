package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Service
public class PDFDataService {

    private static final Logger logger = LoggerFactory.getLogger(PDFDataService.class);

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private static final String ITEM_REGEX = "(.*?)\\s*\\(Código:\\s*\\d+\\)\\s*Qtde\\.:\\s*(\\d+[,.]?\\d*)\\s*UN:\\s*(\\w+)\\s*Vl\\. Unit\\.:\\s*(\\d+[,.]?\\d+)\\s*Vl\\. Total\\s*(\\d+[,.]?\\d+)";

    public void processarDadosEPersistir(String textoPDF, String nomeEstabelecimento, LocalDate dataCadastro) {
        if (isTextoVazioOuNulo(textoPDF, "Erro: O texto do PDF está vazio ou nulo.") ||
                isTextoVazioOuNulo(nomeEstabelecimento, "Erro: O nome do estabelecimento está vazio ou nulo.")) {
            return;
        }

        Estabelecimento estabelecimento = salvarEstabelecimento(nomeEstabelecimento);
        Compra compra = criarCompra(estabelecimento, dataCadastro);
        List<Item> itens = extrairItensDoTexto(textoPDF, compra);

        if (!itens.isEmpty()) {
            for (Item item : itens) {
                item.setCompra(compra);
            }

            compra.setItens(itens);
            compraRepository.save(compra);
            logger.info("Dados processados e salvos com sucesso.");
        } else {
            logger.warn("Nenhum item válido encontrado no texto do PDF.");
        }
    }

    private Estabelecimento salvarEstabelecimento(String nome) {
        return estabelecimentoRepository.findByNomeEstabelecimento(nome)
                .orElseGet(() -> {
                    Estabelecimento novoEstabelecimento = new Estabelecimento();
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
        Pattern pattern = Pattern.compile(ITEM_REGEX);
        Matcher matcher = pattern.matcher(textoPDF);

        List<Item> itens = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        while (matcher.find()) {
            try {
                String nome = matcher.group(1).trim();
                BigDecimal quantidade = new BigDecimal(nf.parse(matcher.group(2)).toString());
                String unidade = matcher.group(3).trim();
                BigDecimal valorUnitario = new BigDecimal(nf.parse(matcher.group(4)).toString());
                BigDecimal valorTotal = new BigDecimal(nf.parse(matcher.group(5)).toString());

                Item item = new Item();
                item.setNome(nome);
                item.setQuantidade(quantidade);
                item.setUnidade(unidade);
                item.setValorUnitario(valorUnitario);
                item.setCompra(compra);
                item.setValorTotal(valorTotal);

                itens.add(item);
                logger.info("Item processado: {}, Qtde: {}, UN: {}, Valor Unit.: {}", nome, quantidade, unidade, valorUnitario);
            } catch (Exception e) {
                logger.error("Erro ao processar item", e);
            }
        }

        return itens;
    }

    private boolean isTextoVazioOuNulo(String texto, String mensagem) {
        if (texto == null || texto.trim().isEmpty()) {
            logger.error(mensagem);
            return true;
        }
        return false;
    }
}
