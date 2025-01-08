package com.marciliojr.compraz.repository;

import java.time.LocalDate;
import java.util.List;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositório responsável por realizar operações de acesso a dados para a entidade Item.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Busca uma lista de itens associados a um estabelecimento específico em uma determinada data de compra.
     *
     * @param nomeEstabelecimento O nome do estabelecimento para filtrar os itens.
     * @param dataCompra          A data de compra para filtrar os itens.
     * @return Uma lista de objetos ItemDTO contendo informações detalhadas sobre os itens.
     *
     * @Query A consulta personalizada utiliza JPQL (Java Persistence Query Language) para buscar os dados,
     *        combinando informações de várias tabelas relacionadas.
     */
    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(" +
            "i.id, i.nome, i.quantidade, i.unidade, i.valorUnitario, " +
            "c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE e.nomeEstabelecimento = :nomeEstabelecimento " +
            "AND c.dataCompra = :dataCompra")
    List<ItemDTO> findAllItemsByEstabelecimentoAndDataCompra(
            @Param("nomeEstabelecimento") String nomeEstabelecimento,
            @Param("dataCompra") LocalDate dataCompra
    );
}
