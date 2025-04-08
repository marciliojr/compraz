package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.model.dto.CompraDTO;
import com.marciliojr.compraz.model.dto.CompraRelatorioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Query("SELECT new com.marciliojr.compraz.model.dto.CompraDTO(" +
            "c.id, e.nomeEstabelecimento, c.dataCompra, SUM(i.valorTotal)) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento LIKE CONCAT('%', :nomeEstabelecimento, '%') " +
            "OR e.nomeEstabelecimento LIKE CONCAT(:nomeEstabelecimento, '%') " +
            "OR e.nomeEstabelecimento LIKE CONCAT('%', :nomeEstabelecimento))" +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim) " +
            "GROUP BY e.nomeEstabelecimento, c.dataCompra")
    List<CompraDTO> findByNomeEstabelecimentoDataCompraValorTotal(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT new com.marciliojr.compraz.model.dto.CompraDTO(" +
            "c.id, e.nomeEstabelecimento, c.dataCompra, SUM(i.valorTotal)) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE e.nomeEstabelecimento = :nomeEstabelecimento " +
            "AND c.dataCompra = :dataCompra " +
            "GROUP BY c.id, e.nomeEstabelecimento, c.dataCompra")
    Optional<CompraDTO> findOneCompraDTOByNomeEstabelecimentoAndDataCompra(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("dataCompra") LocalDate dataCompra);

    @Query("SELECT new com.marciliojr.compraz.model.dto.CompraRelatorioDTO(" +
            "e.nomeEstabelecimento, SUM(i.valorTotal)) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE c.dataCompra BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY e.nomeEstabelecimento")
    List<CompraRelatorioDTO> findRelatorioByDataCompra(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    Boolean existsByEstabelecimentoId(Long idEstabelecimento);

    @Query("SELECT SUM(i.valorTotal) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE (:nomeEstabelecimento IS NULL OR e.nomeEstabelecimento LIKE CONCAT('%', :nomeEstabelecimento, '%') " +
            "OR e.nomeEstabelecimento LIKE CONCAT(:nomeEstabelecimento, '%') " +
            "OR e.nomeEstabelecimento LIKE CONCAT('%', :nomeEstabelecimento))" +
            "AND (:tipoCupom IS NULL OR e.tipoCupom = :tipoCupom) " +
            "AND (:dataInicio IS NULL OR c.dataCompra >= :dataInicio) " +
            "AND (:dataFim IS NULL OR c.dataCompra <= :dataFim)")
    BigDecimal sumValorTotalByEstabelecimentoAndPeriodo(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("tipoCupom") TipoCupom tipoCupom,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
}
