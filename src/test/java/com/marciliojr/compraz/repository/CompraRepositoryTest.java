package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompraRepositoryTest {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    void deveSalvarERecuperarCompra() {
        // Criando estabelecimento
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Supermercado X");
        estabelecimento = estabelecimentoRepository.save(estabelecimento);

        // Criando uma compra associada ao estabelecimento
        Compra compra = new Compra();
        compra.setEstabelecimento(estabelecimento);
        compra.setDataCompra(LocalDate.of(2024, 1, 20));
        compra = compraRepository.save(compra);

        // Verificando se a compra foi salva corretamente
        Optional<Compra> compraSalva = compraRepository.findById(compra.getId());
        assertThat(compraSalva).isPresent();
        assertThat(compraSalva.get().getEstabelecimento().getNomeEstabelecimento()).isEqualTo("Supermercado X");
    }
}
