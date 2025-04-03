package com.marciliojr.compraz.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum TipoCupom {
    TODOS(0, "Todos"),
    MERCADO(1, "Mercado"),
    LOJA(2, "Loja"),
    FARMACIA(3, "Farmácia"),
    OUTROS(4, "Outros");

    private final int codigo;
    private final String descricao;

    TipoCupom(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoCupom obterPorCodigo(Integer codigo) {
        if (Objects.isNull(codigo) || TipoCupom.TODOS.codigo == codigo) {
            return null;
        }
        for (TipoCupom tipo : values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }

    public static List<TipoCupom> obterTodos() {
        return Arrays.asList(values());
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return descricao != null ? descricao : "";
    }
}
