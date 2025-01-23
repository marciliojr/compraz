package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PDFDataService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private static final String ITEM_REGEX = "(.*?)\\s+\\(Código: \\d+\\)\\s+Qtde\\.:\\s+(\\d+,?\\d*)\\s+UN:\\s+(\\w+)\\s+Vl\\. Unit\\.:\\s+(\\d+,\\d+)";

    public void processarDadosEPersistir(String textoPDF, String nomeEstabelecimento) {
        if (isTextoVazioOuNulo(textoPDF, "Erro: O texto do PDF está vazio ou nulo.") ||
                isTextoVazioOuNulo(nomeEstabelecimento, "Erro: O nome do estabelecimento está vazio ou nulo.")) {
            return;
        }

        Estabelecimento estabelecimento = salvarEstabelecimento(nomeEstabelecimento);
        Compra compra = criarCompra(estabelecimento);
        List<Item> itens = extrairItensDoTexto(textoPDF, compra);

        if (!itens.isEmpty()) {
            compra.setItens(itens);
            compraRepository.save(compra);
            System.out.println("Dados processados e salvos com sucesso.");
        } else {
            System.err.println("Nenhum item válido encontrado no texto do PDF.");
        }
    }

    private Estabelecimento salvarEstabelecimento(String nome) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento(nome);
        return estabelecimentoRepository.save(estabelecimento);
    }

    private Compra criarCompra(Estabelecimento estabelecimento) {
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setEstabelecimento(estabelecimento);
        return compra;
    }

    private List<Item> extrairItensDoTexto(String textoPDF, Compra compra) {
        Pattern pattern = Pattern.compile(ITEM_REGEX);
        Matcher matcher = pattern.matcher(textoPDF);

        List<Item> itens = new ArrayList<>();
        while (matcher.find()) {
            try {
                String nome = matcher.group(1).trim();
                BigDecimal quantidade = new BigDecimal(matcher.group(2).replace(",", ".").trim());
                String unidade = matcher.group(3).trim();
                BigDecimal valorUnitario = new BigDecimal(matcher.group(4).replace(",", ".").trim());

                Item item = new Item();
                item.setNome(nome);
                item.setQuantidade(quantidade);
                item.setUnidade(unidade);
                item.setValorUnitario(valorUnitario);
                item.setCompra(compra);

                itens.add(item);
                System.out.printf("Item processado: %s, Qtde: %s, UN: %s, Valor Unit.: %s%n", nome, quantidade, unidade, valorUnitario);
            } catch (Exception e) {
                System.err.println("Erro ao processar item: " + e.getMessage());
            }
        }

        return itens;
    }

    private boolean isTextoVazioOuNulo(String texto, String mensagem) {
        if (texto == null || texto.trim().isEmpty()) {
            System.err.println(mensagem);
            return true;
        }
        return false;
    }
}