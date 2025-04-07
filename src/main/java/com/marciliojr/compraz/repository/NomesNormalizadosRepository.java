package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.NomesNormalizados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomesNormalizadosRepository extends JpaRepository<NomesNormalizados, Long> {


}
