package com.marciliojr.compraz.infra;


import com.marciliojr.compraz.model.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemNormalizer {

    private static final Map<String, String> abreviacoes;

    static {
        abreviacoes = new HashMap<>();

        abreviacoes.put("LTE", "LEITE");
        abreviacoes.put("QJO", "QUEIJO");
        abreviacoes.put("CHOC", "CHOCOLATE");
        abreviacoes.put("COND", "CONDENSADO");
        abreviacoes.put("INT", "INTEGRAL");
        abreviacoes.put("MOR", "MORANGO");
        abreviacoes.put("DESINF", "DESINFETANTE");
        abreviacoes.put("TRAD", "TRADICIONAL");
        abreviacoes.put("LACT", "LACTEA");
        abreviacoes.put("CEN", "CENOURA");
        abreviacoes.put("CEB", "CEBOLA");
        abreviacoes.put("S/OSSO", "SEM OSSO");
        abreviacoes.put("PARM", "PARMESAO");
        abreviacoes.put("PO", "PO");
        abreviacoes.put("LT", "LATA");
        abreviacoes.put("PCT", "PACOTE");
        abreviacoes.put("UND", "UNIDADE");
        abreviacoes.put("REF", "REFIL");
        abreviacoes.put("INST", "INSTANTANEO");
        abreviacoes.put("FD", "FARDO");
        abreviacoes.put("PC", "PACOTE");
        abreviacoes.put("PPAO", "PARA PAO");
        abreviacoes.put("SCH", "SACHE");
        abreviacoes.put("TEMP", "TEMPERADO");
        abreviacoes.put("SH", "SACHE");
        abreviacoes.put("KG", "QUILOGRAMA");
        abreviacoes.put("UN", "UNIDADE");
        abreviacoes.put("BDJ", "BANDEJA");
        abreviacoes.put("GF", "GARRAFA");
        abreviacoes.put("L", "LEITE");
        abreviacoes.put("FP", "FRASCO PET");
        abreviacoes.put("TP", "TETRA PAK");
        abreviacoes.put("CX", "CAIXA");
        abreviacoes.put("CCAROCO", "COM CAROCO");
        abreviacoes.put("ML", "MILILITROS");
        abreviacoes.put("G", "GRAMAS");
        abreviacoes.put("VC", "VIDRO");
        abreviacoes.put("VD", "VIDRO");
        abreviacoes.put("SOLIDAR", "SOLIDARIA");
        abreviacoes.put("AER", "AEROSOL");
        abreviacoes.put("PP", "PEITO DE PERU");
        abreviacoes.put("PRO", "PROVOLONE");
        abreviacoes.put("ALUM", "ALUMINIO");
        abreviacoes.put("SEMI", "SEMIDESNATADO");
        abreviacoes.put("DESOD", "DESODORANTE");
        abreviacoes.put("BAMB", "BAMBOO");
        abreviacoes.put("OLDSPI", "OLD SPICE");
        abreviacoes.put("CHOCO", "CHOCOLATE");
        abreviacoes.put("BRIOCHE", "BRIOCHE");
        abreviacoes.put("CAESAR", "CAESAR");
        abreviacoes.put("CLASSIC", "CLASSICO");
        abreviacoes.put("VITARELLA", "VITARELLA");
        abreviacoes.put("TRELOSO", "TRELOSO");
        abreviacoes.put("PLUSVITA", "PLUS VITA");
        abreviacoes.put("MOCA", "MOCA");
        abreviacoes.put("GORGONZOLA", "GORGONZOLA");
        abreviacoes.put("YOPRO", "YOPRO");
        abreviacoes.put("NINHO", "NINHO");
        abreviacoes.put("UTILIS", "UTILIS");
        abreviacoes.put("HORTIFRUTICOLAS", "HORTIFRUTICOLAS");
        abreviacoes.put("SAB", "SABONETE");
        abreviacoes.put("EXT", "EXTRATO");
        abreviacoes.put("FAR ", "FARINHA");
        abreviacoes.put("IG", "IOGURTE");
        abreviacoes.put("REQ", "REQUEIJAO");
        abreviacoes.put("CR", "CREME");

    }

    public static Map<String, String> getMapaabreviacoes() {
        return Collections.unmodifiableMap(abreviacoes);
    }

    public static void normalizarNomes(List<Item> itens) {
        for (Item item : itens) {
            String[] palavras = item.getNome().trim().split(" ");
            for (int i = 0; i < palavras.length; i++) {
                String chave = palavras[i].toUpperCase();
                if (getMapaabreviacoes().containsKey(chave)) {
                    palavras[i] = getMapaabreviacoes().get(chave);
                }
            }
            item.setNome(String.join(" ", palavras));
        }
    }
}
