package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


    @Query("SELECT new com.marciliojr.compraz.model.dto.ItemDTO(i.id, i.nome, i.quantidade, i.unidade, i.valorUnitario, c.dataCompra, e.nomeEstabelecimento) " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE e.nomeEstabelecimento = :nomeEstabelecimento " +
            "AND c.dataCompra = :dataCompra")
    List<ItemDTO> findAllItemsByEstabelecimentoAndDataCompra(@Param("nomeEstabelecimento") String nomeEstabelecimento,
                                                             @Param("dataCompra") LocalDate dataCompra);


}
