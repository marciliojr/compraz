package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstabelecimentoRepository  extends JpaRepository<Estabelecimento, Long> {

    @Query("SELECT e FROM Estabelecimento e")
    List<Estabelecimento> findAllEstabelecimento();

}
