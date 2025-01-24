package com.marciliojr.compraz.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento = :nomeEstabelecimento) " +
            "AND (:dataInicio IS NULL OR :dataFim IS NULL OR c.dataCompra BETWEEN :dataInicio AND :dataFim)")
    List<ItemDTO> findAllItemsByEstabelecimentoAndPeriodo(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    @Query("SELECT COALESCE(SUM(i.valorUnitario), 0) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento = :nomeEstabelecimento) " +
            "AND (:dataInicio IS NOT NULL AND c.dataCompra >= :dataInicio OR :dataInicio IS NULL) " +
            "AND (:dataFim IS NOT NULL AND c.dataCompra <= :dataFim OR :dataFim IS NULL)")
    BigDecimal sumValorUnitarioByEstabelecimentoAndPeriodo(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );



}
