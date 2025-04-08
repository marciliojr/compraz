package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.TipoCupom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    void deveSalvarEstabelecimento() {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);

        Estabelecimento estabelecimentoSalvo = estabelecimentoRepository.save(estabelecimento);

        assertThat(estabelecimentoSalvo.getId()).isNotNull();
        assertThat(estabelecimentoSalvo.getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(estabelecimentoSalvo.getTipoCupom()).isEqualTo(TipoCupom.MERCADO);
    }

    @Test
    void deveBuscarTodosEstabelecimentos() {

        Estabelecimento estabelecimento1 = new Estabelecimento();
        estabelecimento1.setNomeEstabelecimento("Mercado Teste 1");
        estabelecimento1.setTipoCupom(TipoCupom.MERCADO);

        Estabelecimento estabelecimento2 = new Estabelecimento();
        estabelecimento2.setNomeEstabelecimento("Farmácia Teste");
        estabelecimento2.setTipoCupom(TipoCupom.FARMACIA);

        estabelecimentoRepository.save(estabelecimento1);
        estabelecimentoRepository.save(estabelecimento2);


        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAllEstabelecimento();


        assertThat(estabelecimentos).hasSize(2);
        assertThat(estabelecimentos)
                .extracting(Estabelecimento::getNomeEstabelecimento)
                .containsExactlyInAnyOrder("Mercado Teste 1", "Farmácia Teste");
    }

    @Test
    void deveBuscarEstabelecimentoPorNome() {

        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);
        estabelecimentoRepository.save(estabelecimento);

        Optional<Estabelecimento> estabelecimentoEncontrado = estabelecimentoRepository.findByNomeEstabelecimento("Mercado Teste");

        assertThat(estabelecimentoEncontrado).isPresent();
        assertThat(estabelecimentoEncontrado.get().getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        assertThat(estabelecimentoEncontrado.get().getTipoCupom()).isEqualTo(TipoCupom.MERCADO);
    }

    @Test
    void deveRetornarVazioQuandoBuscarEstabelecimentoInexistente() {

        Optional<Estabelecimento> estabelecimentoEncontrado = estabelecimentoRepository.findByNomeEstabelecimento("Estabelecimento Inexistente");

        assertThat(estabelecimentoEncontrado).isEmpty();
    }
} 