package com.marciliojr.compraz.infra;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ItemNormalizer {

    private static final Map<String, String> abreviacoes;

    static {
        abreviacoes = new HashMap<>();
        abreviacoes.put("AZEIT", "AZEITE");
        abreviacoes.put("AZEIT ", "AZEITE");
        abreviacoes.put("EXT", "EXTRATO");
        abreviacoes.put("EXT ", "EXTRATO");
        abreviacoes.put("IOG", "IOGURTE");
        abreviacoes.put("IOG ", "IOGURTE");
        abreviacoes.put("SOBCOX", "SOBRE COXA");
        abreviacoes.put("SOBCOX ", "SOBRE COXA");
        abreviacoes.put("BEB", "BEBIDA");
        abreviacoes.put("BEB ", "BEBIDA");
        abreviacoes.put("LACT", "LACTEA");
        abreviacoes.put("LACT ", "LACTEA");
        abreviacoes.put("FERM", "FERMENTADO");
        abreviacoes.put("FERM ", "FERMENTADO");
        abreviacoes.put("IOG NAT", "IOGURTE NATURAL");
        abreviacoes.put("IOG NAT ", "IOGURTE NATURAL");
        abreviacoes.put("QJO", "IOGURTE NATURAL");
        abreviacoes.put("QJO ", "IOGURTE NATURAL");
        abreviacoes.put("SAB", "SABONETE");
        abreviacoes.put("SAB ", "SABONETE");
        abreviacoes.put("DES", "DESODORANTE");
        abreviacoes.put("DES ", "DESODORANTE");
        abreviacoes.put("AZ", "AZEITE");
        abreviacoes.put("AZ ", "AZEITE");
        abreviacoes.put("MAC", "MACARRAO");
        abreviacoes.put("MAC ", "MACARRAO");
        abreviacoes.put("CHIC", "CHICLETE");
        abreviacoes.put("CHIC ", "CHICLETE");
        abreviacoes.put("CHOC", "CHOCOLATE");
        abreviacoes.put("CHOC ", "CHOCOLATE");
        abreviacoes.put("FAR", "FARINHA");
        abreviacoes.put("FAR ", "FARINHA");
        abreviacoes.put("ANT", "ANTISEPTICO");
        abreviacoes.put("ANT ", "ANTISEPTICO");
        abreviacoes.put("CR", "CREME");
        abreviacoes.put("CR ", "CREME");
        abreviacoes.put("QJO", "QUEIJO");
        abreviacoes.put("QJO ", "QUEIJO");
        abreviacoes.put("BISC", "BISCOITO");
        abreviacoes.put("BISC ", "BISCOITO");
        abreviacoes.put("LIMP", "LIMPADOR");
        abreviacoes.put("LIMP ", "LIMPADOR");
        abreviacoes.put("DES", "DESODORANTE");
        abreviacoes.put("DES ", "DESODORANTE");
        abreviacoes.put("REFRIG", "REFRIGERANTE");
        abreviacoes.put("REFRIG ", "REFRIGERANTE");
        abreviacoes.put("CR.LEITE", "CREME DE LEITE");
        abreviacoes.put("CR.LEITE ", "CREME DE LEITE");
        abreviacoes.put("SAC", "SACOLA");
        abreviacoes.put("SAC ", "SACOLA");
        abreviacoes.put("SAB.LIQ", "SABONETE LIQUIDO");
        abreviacoes.put("SAB.LIQ ", "SABONETE LIQUIDO");
        abreviacoes.put("QUEIJO MUSS.", "QUEIJO MUSSARELA");
        abreviacoes.put("QUEIJO MUSS. ", "QUEIJO MUSSARELA");
        abreviacoes.put("SALG", "SALGADO");
        abreviacoes.put("SALG ", "SALGADO");
        abreviacoes.put("CATUP", "CATUPIRY");
        abreviacoes.put("CATUP ", "CATUPIRY");
        abreviacoes.put("LIMON", "LIMONETO");
        abreviacoes.put("LIMON ", "LIMONETO");
        abreviacoes.put("GRAN", "GRANULADO");
        abreviacoes.put("GRAN ", "GRANULADO");
        abreviacoes.put("MARG", "MARGARINA");
        abreviacoes.put("MARG ", "MARGARINA");
    }

    public static Map<String, String> getMapaabreviacoes() {
        return Collections.unmodifiableMap(abreviacoes);
    }
}
