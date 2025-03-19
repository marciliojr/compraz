package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Estabelecimento;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Disabled
public class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    void deveSalvarERecuperarEstabelecimento() {
//        Estabelecimento estabelecimento = new Estabelecimento();
//        estabelecimento.setNomeEstabelecimento("Mercado Y");
//        estabelecimento = estabelecimentoRepository.save(estabelecimento);
//
//        Optional<Estabelecimento> encontrado = estabelecimentoRepository.findByNomeEstabelecimento("Mercado Y");
//
//        assertThat(encontrado).isPresent();
//        assertThat(encontrado.get().getNomeEstabelecimento()).isEqualTo("Mercado Y");
    }

    @Test
    void deveBuscarTodosEstabelecimentos() {
        //estabelecimentoRepository.save(new Estabelecimento(null, "Loja A"));
        //estabelecimentoRepository.save(new Estabelecimento(null, "Loja B"));

        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAllEstabelecimento();

        assertThat(estabelecimentos).hasSize(2);
    }
}
