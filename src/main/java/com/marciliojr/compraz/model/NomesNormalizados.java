package com.marciliojr.compraz.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "nomes_normalizados")
public class NomesNormalizados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_mercado", nullable = false)
    private String abreviacaoMercado;
    @Column(name = "nome_regularizado", nullable = false)
    private String nomeRegularizado;

    public NomesNormalizados() {
    }

    public NomesNormalizados(String abreviacaoMercado, String nomeRegularizado) {
        this.abreviacaoMercado = abreviacaoMercado;
        this.nomeRegularizado = nomeRegularizado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviacaoMercado() {
        return abreviacaoMercado;
    }

    public void setAbreviacaoMercado(String abreviacaoMercado) {
        this.abreviacaoMercado = abreviacaoMercado;
    }

    public String getNomeRegularizado() {
        return nomeRegularizado;
    }

    public void setNomeRegularizado(String nomeRegularizado) {
        this.nomeRegularizado = nomeRegularizado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NomesNormalizados that = (NomesNormalizados) o;
        return Objects.equals(id, that.id) && Objects.equals(abreviacaoMercado, that.abreviacaoMercado) && Objects.equals(nomeRegularizado, that.nomeRegularizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, abreviacaoMercado, nomeRegularizado);
    }

    @Override
    public String toString() {
        return "NomesNormalizados{" +
                "id=" + id +
                ", abreviacaoMercado='" + abreviacaoMercado + '\'' +
                ", nomeRegularizado='" + nomeRegularizado + '\'' +
                '}';
    }
}
