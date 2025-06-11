package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.RelatorioItemDTO;
import com.marciliojr.compraz.model.dto.TopProdutosDTO;
import com.marciliojr.compraz.model.dto.RelatorioComparativoDTO;
import com.marciliojr.compraz.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    private final ItemRepository itemRepository;

    @Autowired
    public RelatorioService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<RelatorioItemDTO> gerarRelatorioItensAgrupados(LocalDate dataInicio, LocalDate dataFim) {
        List<Item> itens = itemRepository.findByCompraDataCompraBetween(dataInicio, dataFim);
        
        Map<String, List<Item>> itensAgrupados = itens.stream()
                .collect(Collectors.groupingBy(item -> normalizarNomeItem(item.getNome())));

        return itensAgrupados.entrySet().stream()
                .map(entry -> {
                    String nomeNormalizado = entry.getKey();
                    List<Item> itensDoGrupo = entry.getValue();
                    
                    BigDecimal quantidadeTotal = itensDoGrupo.stream()
                            .map(Item::getQuantidade)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    
                    BigDecimal mediaPreco = itensDoGrupo.stream()
                            .map(Item::getValorUnitario)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(new BigDecimal(itensDoGrupo.size()), 2, RoundingMode.HALF_UP);
                    
                    return new RelatorioItemDTO(nomeNormalizado, quantidadeTotal, mediaPreco);
                })
                .sorted(Comparator.comparing(RelatorioItemDTO::getNomeItem))
                .collect(Collectors.toList());
    }

    /**
     * Gera relatório dos produtos mais comprados para exibição em gráfico de barras horizontais
     * @param dataInicio Data inicial do período
     * @param dataFim Data final do período
     * @param limite Número máximo de produtos a retornar (top N produtos)
     * @return Lista dos top produtos ordenados por quantidade total (decrescente), excluindo sacolas
     */
    public List<TopProdutosDTO> gerarTopProdutosMaisComprados(LocalDate dataInicio, LocalDate dataFim, Integer limite) {
        List<RelatorioItemDTO> relatorioCompleto = gerarRelatorioItensAgrupados(dataInicio, dataFim);
        
        return relatorioCompleto.stream()
                .filter(item -> !item.getNomeItem().toLowerCase().contains("sacola")) // Exclui produtos que contenham "sacola"
                .map(item -> new TopProdutosDTO(item.getNomeItem(), item.getQuantidadeTotal()))
                .sorted(Comparator.comparing(TopProdutosDTO::getQuantidadeTotal).reversed())
                .limit(limite != null ? limite : 10) // Padrão de 10 produtos se não especificado
                .collect(Collectors.toList());
    }

    private String normalizarNomeItem(String nome) {
        // Remove caracteres especiais e converte para minúsculas
        String nomeNormalizado = nome.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim();

        // Extrai a primeira palavra do nome
        String primeiraPalavra = nomeNormalizado.split("\\s+")[0];

        // Mapeia palavras comuns para seus grupos
        Map<String, String> mapeamentoGrupos = new HashMap<>();
        mapeamentoGrupos.put("arroz", "Arroz");
        mapeamentoGrupos.put("feijao", "Feijão");
        mapeamentoGrupos.put("macarrao", "Macarrão");
        mapeamentoGrupos.put("cafe", "Café");
        mapeamentoGrupos.put("leite", "Leite");
        mapeamentoGrupos.put("pao", "Pão");
        mapeamentoGrupos.put("carne", "Carne");
        mapeamentoGrupos.put("frango", "Frango");
        mapeamentoGrupos.put("peixe", "Peixe");
        mapeamentoGrupos.put("ovo", "Ovo");
        mapeamentoGrupos.put("queijo", "Queijo");
        mapeamentoGrupos.put("manteiga", "Manteiga");
        mapeamentoGrupos.put("margarina", "Margarina");
        mapeamentoGrupos.put("acucar", "Açúcar");
        mapeamentoGrupos.put("sal", "Sal");
        mapeamentoGrupos.put("oleo", "Óleo");
        mapeamentoGrupos.put("farinha", "Farinha");
        mapeamentoGrupos.put("biscoito", "Biscoito");
        mapeamentoGrupos.put("bolacha", "Bolacha");
        mapeamentoGrupos.put("chocolate", "Chocolate");
        mapeamentoGrupos.put("refrigerante", "Refrigerante");
        mapeamentoGrupos.put("suco", "Suco");
        mapeamentoGrupos.put("agua", "Água");
        mapeamentoGrupos.put("cerveja", "Cerveja");
        mapeamentoGrupos.put("vinho", "Vinho");
        mapeamentoGrupos.put("sabao", "Sabão");
        mapeamentoGrupos.put("detergente", "Detergente");
        mapeamentoGrupos.put("papel", "Papel");
        mapeamentoGrupos.put("fralda", "Fralda");
        mapeamentoGrupos.put("shampoo", "Shampoo");
        mapeamentoGrupos.put("condicionador", "Condicionador");
        mapeamentoGrupos.put("creme", "Creme");
        mapeamentoGrupos.put("desodorante", "Desodorante");
        mapeamentoGrupos.put("pasta", "Pasta de Dente");
        mapeamentoGrupos.put("escova", "Escova de Dente");
        mapeamentoGrupos.put("absorvente", "Absorvente");
        mapeamentoGrupos.put("fio", "Fio Dental");
        mapeamentoGrupos.put("alcool", "Álcool");
        mapeamentoGrupos.put("agua", "Água Sanitária");
        mapeamentoGrupos.put("desinfetante", "Desinfetante");
        mapeamentoGrupos.put("limpa", "Limpa Vidros");
        mapeamentoGrupos.put("lustra", "Lustra Móveis");
        mapeamentoGrupos.put("vassoura", "Vassoura");
        mapeamentoGrupos.put("rodo", "Rodo");
        mapeamentoGrupos.put("pano", "Pano");
        mapeamentoGrupos.put("esponja", "Esponja");
        mapeamentoGrupos.put("luvas", "Luvas");
        mapeamentoGrupos.put("saco", "Saco de Lixo");
        mapeamentoGrupos.put("fita", "Fita Adesiva");
        mapeamentoGrupos.put("cola", "Cola");
        mapeamentoGrupos.put("tesoura", "Tesoura");
        mapeamentoGrupos.put("caneta", "Caneta");
        mapeamentoGrupos.put("lapis", "Lápis");
        mapeamentoGrupos.put("borracha", "Borracha");
        mapeamentoGrupos.put("apontador", "Apontador");
        mapeamentoGrupos.put("caderno", "Caderno");
        mapeamentoGrupos.put("folha", "Folha Sulfite");
        mapeamentoGrupos.put("cartolina", "Cartolina");
        mapeamentoGrupos.put("eva", "EVA");
        mapeamentoGrupos.put("cola", "Cola");
        mapeamentoGrupos.put("tesoura", "Tesoura");
        mapeamentoGrupos.put("caneta", "Caneta");
        mapeamentoGrupos.put("lapis", "Lápis");
        mapeamentoGrupos.put("borracha", "Borracha");
        mapeamentoGrupos.put("apontador", "Apontador");
        mapeamentoGrupos.put("caderno", "Caderno");
        mapeamentoGrupos.put("folha", "Folha Sulfite");
        mapeamentoGrupos.put("cartolina", "Cartolina");
        mapeamentoGrupos.put("eva", "EVA");

        return mapeamentoGrupos.getOrDefault(primeiraPalavra, nome);
    }

    /**
     * Gera relatório comparativo de preços de todos os produtos entre estabelecimentos
     * comparando mês atual com mês anterior
     * @param dataInicio Data inicial do período para comparação
     * @param dataFim Data final do período para comparação  
     * @return Lista de relatórios comparativos para todos os produtos do período
     */
    public List<RelatorioComparativoDTO> gerarRelatorioComparativoPrecos(LocalDate dataInicio, LocalDate dataFim) {
        // Calcula o período anterior com a mesma duração
        long diasPeriodo = dataInicio.until(dataFim).getDays() + 1;
        LocalDate dataInicioAnterior = dataInicio.minusDays(diasPeriodo);
        LocalDate dataFimAnterior = dataInicio.minusDays(1);
        
        // Busca todos os itens do período atual
        List<Item> itensPeriodoAtual = itemRepository.findByCompraDataCompraBetween(dataInicio, dataFim);
        
        // Busca todos os itens do período anterior
        List<Item> itensPeriodoAnterior = itemRepository.findByCompraDataCompraBetween(dataInicioAnterior, dataFimAnterior);
        
        // Agrupa por nome normalizado para período atual
        Map<String, List<Item>> itensAgrupadosPeriodoAtual = itensPeriodoAtual.stream()
                .collect(Collectors.groupingBy(item -> normalizarNomeItem(item.getNome())));
                
        // Agrupa por nome normalizado para período anterior
        Map<String, List<Item>> itensAgrupadosPeriodoAnterior = itensPeriodoAnterior.stream()
                .collect(Collectors.groupingBy(item -> normalizarNomeItem(item.getNome())));
        
        // Obtém todos os nomes únicos dos dois períodos
        Set<String> todosNomesItens = new HashSet<>();
        todosNomesItens.addAll(itensAgrupadosPeriodoAtual.keySet());
        todosNomesItens.addAll(itensAgrupadosPeriodoAnterior.keySet());
        
        // Gera relatório para cada item
        return todosNomesItens.stream()
                .map(nomeItem -> {
                    List<Item> itensPeriodoAtualItem = itensAgrupadosPeriodoAtual.getOrDefault(nomeItem, new ArrayList<>());
                    List<Item> itensPeriodoAnteriorItem = itensAgrupadosPeriodoAnterior.getOrDefault(nomeItem, new ArrayList<>());
                    
                    List<RelatorioComparativoDTO.ComparacaoEstabelecimentoDTO> comparacoesPeriodoAtual = 
                            agruparPorEstabelecimento(itensPeriodoAtualItem);
                            
                    List<RelatorioComparativoDTO.ComparacaoEstabelecimentoDTO> comparacoesPeriodoAnterior = 
                            agruparPorEstabelecimento(itensPeriodoAnteriorItem);
                    
                    return new RelatorioComparativoDTO(
                            nomeItem,
                            comparacoesPeriodoAtual,
                            comparacoesPeriodoAnterior
                    );
                })
                .filter(relatorio -> !relatorio.getComparacoesMesAtual().isEmpty() || !relatorio.getComparacoesMesAnterior().isEmpty())
                .sorted(Comparator.comparing(RelatorioComparativoDTO::getNomeItem))
                .collect(Collectors.toList());
    }
    
    private List<RelatorioComparativoDTO.ComparacaoEstabelecimentoDTO> agruparPorEstabelecimento(List<Item> itens) {
        Map<String, List<Item>> itensPorEstabelecimento = itens.stream()
                .collect(Collectors.groupingBy(item -> item.getCompra().getEstabelecimento().getNomeEstabelecimento()));
                
        return itensPorEstabelecimento.entrySet().stream()
                .map(entry -> {
                    String estabelecimento = entry.getKey();
                    List<Item> itensEstabelecimento = entry.getValue();
                    
                    BigDecimal precoMedio = itensEstabelecimento.stream()
                            .map(Item::getValorUnitario)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .divide(new BigDecimal(itensEstabelecimento.size()), 2, RoundingMode.HALF_UP);
                            
                    BigDecimal quantidadeTotal = itensEstabelecimento.stream()
                            .map(Item::getQuantidade)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                            
                    Integer totalCompras = itensEstabelecimento.size();
                    
                    return new RelatorioComparativoDTO.ComparacaoEstabelecimentoDTO(
                            estabelecimento,
                            precoMedio,
                            quantidadeTotal,
                            totalCompras
                    );
                })
                .sorted(Comparator.comparing(RelatorioComparativoDTO.ComparacaoEstabelecimentoDTO::getPrecoMedio))
                .collect(Collectors.toList());
    }
} 