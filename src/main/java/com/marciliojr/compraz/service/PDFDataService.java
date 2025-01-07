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

/**
 * Serviço responsável por processar dados extraídos de um PDF e persistir informações de compras, estabelecimentos e itens no banco de dados.
 */
@Service
public class PDFDataService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private static final String ITEM_REGEX = "(.*?)\\s+\\(Código: \\d+\\)\\s+Qtde\\.:\\s+(\\d+,?\\d*)\\s+UN:\\s+(\\w+)\\s+Vl\\. Unit\\.:\\s+(\\d+,\\d+)";

    /**
     * Processa o texto extraído de um PDF e salva os dados de compra e itens no banco de dados.
     *
     * @param textoPDF             O texto extraído do PDF.
     * @param nomeEstabelecimento  O nome do estabelecimento relacionado à compra.
     */
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

    /**
     * Salva um novo estabelecimento no banco de dados.
     *
     * @param nome Nome do estabelecimento.
     * @return Entidade do estabelecimento salva.
     */
    private Estabelecimento salvarEstabelecimento(String nome) {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento(nome);
        return estabelecimentoRepository.save(estabelecimento);
    }

    /**
     * Cria uma nova compra associada a um estabelecimento.
     *
     * @param estabelecimento Estabelecimento relacionado à compra.
     * @return Entidade de compra criada.
     */
    private Compra criarCompra(Estabelecimento estabelecimento) {
        Compra compra = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setEstabelecimento(estabelecimento);
        return compra;
    }

    /**
     * Extrai os itens do texto do PDF usando o regex definido.
     *
     * @param textoPDF Texto do PDF a ser processado.
     * @param compra   Compra à qual os itens serão associados.
     * @return Lista de itens extraídos.
     */
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

    /**
     * Verifica se o texto fornecido está vazio ou nulo e exibe uma mensagem de erro se necessário.
     *
     * @param texto    Texto a ser validado.
     * @param mensagem Mensagem de erro a ser exibida caso o texto esteja inválido.
     * @return {@code true} se o texto estiver vazio ou nulo, caso contrário {@code false}.
     */
    private boolean isTextoVazioOuNulo(String texto, String mensagem) {
        if (texto == null || texto.trim().isEmpty()) {
            System.err.println(mensagem);
            return true;
        }
        return false;
    }
}
