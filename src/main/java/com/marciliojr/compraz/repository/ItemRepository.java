package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i " +
            "FROM Item i " +
            "JOIN i.compra c " +
            "JOIN c.estabelecimento e " +
            "WHERE c.dataCompra = :dataCompra " +
            "AND e.nomeEstabelecimento = :nomeEstabelecimento")
    List<Item> findAllByDataCompraAndEstabelecimento(@Param("dataCompra") LocalDate dataCompra,
                                                     @Param("nomeEstabelecimento") String nomeEstabelecimento);

//    @Query("SELECT new com.marciliojr.compraz.dto.ItemDTO(i.id, i.nome, i.quantidade, i.unidade, i.valorUnitario, c.dataCompra, e.nomeEstabelecimento) " +
//            "FROM Item i " +
//            "JOIN i.compra c " +
//            "JOIN c.estabelecimento e " +
//            "WHERE e.id = :idEstabelecimento " +
//            "AND c.dataCompra = :dataCompra")
//    List<ItemDTO> findAllItemsByEstabelecimentoAndDataCompra(@Param("idEstabelecimento") Long idEstabelecimento,
//                                                             @Param("dataCompra") LocalDate dataCompra);


}
