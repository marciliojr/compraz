package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository <Compra, Long>  {

}
