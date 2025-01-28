package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstabelecimentoRepository  extends JpaRepository<Estabelecimento, Long> {

    @Query("SELECT e FROM Estabelecimento e")
    List<Estabelecimento> findAllEstabelecimento();

    Optional<Estabelecimento> findByNomeEstabelecimento(String nomeEstabelecimento);

}
