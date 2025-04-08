package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.ItemDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorTotal, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento = :nomeEstabelecimento) " +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim)")
    List<ItemDTO> findAllItemsByEstabelecimentoAndPeriodo(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT COALESCE(SUM(i.valorTotal), 0) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento = :nomeEstabelecimento) " +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim)")
    BigDecimal sumValorTotalByEstabelecimentoAndPeriodo(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorTotal, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nome IS NULL OR LOWER(i.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim)" +
            "AND (:nomeEstabelecimento IS NULL OR LOWER(e.nomeEstabelecimento) LIKE LOWER(CONCAT('%', :nomeEstabelecimento, '%')))")
    List<ItemDTO> findByNomeByPeriodo(
            @Param("nome") String nome,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("nomeEstabelecimento") String nomeEstabelecimento);

    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorTotal, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nome IS NULL OR LOWER(i.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) " +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim)" +
            "AND (:nomeEstabelecimento IS NULL OR LOWER(e.nomeEstabelecimento) LIKE LOWER(CONCAT('%', :nomeEstabelecimento, '%'))) " +
            "ORDER BY i.nome asc")
    Page<ItemDTO> findByNomeByPeriodoPaginado(
            @Param("nome") String nome,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            Pageable pageable);


    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.compra.id = :compraId")
    void deleteByCompraId(@Param("compraId") Long compraId);

    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorTotal, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE c.id = :compraId " +
            "ORDER BY i.nome")
    List<ItemDTO> findByCompraId(@Param("compraId") Long compraId);


}