package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.NomesNormalizados;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class NomesNormalizadosRepositoryTest {

    @Autowired
    private NomesNormalizadosRepository repository;

    @Test
    void deveSalvarUmNovoNomeNormalizado() {
        NomesNormalizados nomeNormalizado = new NomesNormalizados("EXTRA", "EXTRA SUPERMERCADOS");
        
        NomesNormalizados saved = repository.save(nomeNormalizado);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAbreviacaoMercado()).isEqualTo("EXTRA");
        assertThat(saved.getNomeRegularizado()).isEqualTo("EXTRA SUPERMERCADOS");
    }

    @Test
    void deveEncontrarPorId() {
        NomesNormalizados nomeNormalizado = new NomesNormalizados("CARREFOUR", "CARREFOUR BRASIL");
        NomesNormalizados saved = repository.save(nomeNormalizado);
        
        Optional<NomesNormalizados> found = repository.findById(saved.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getAbreviacaoMercado()).isEqualTo("CARREFOUR");
        assertThat(found.get().getNomeRegularizado()).isEqualTo("CARREFOUR BRASIL");
    }

    @Test
    void deveListarTodosOsNomesNormalizados() {
        NomesNormalizados nome1 = new NomesNormalizados("EXTRA", "EXTRA SUPERMERCADOS");
        NomesNormalizados nome2 = new NomesNormalizados("CARREFOUR", "CARREFOUR BRASIL");
        
        repository.save(nome1);
        repository.save(nome2);
        
        List<NomesNormalizados> todos = repository.findAll();
        
        assertThat(todos).hasSize(2);
        assertThat(todos).extracting(NomesNormalizados::getAbreviacaoMercado)
                .containsExactlyInAnyOrder("EXTRA", "CARREFOUR");
    }

    @Test
    void deveAtualizarUmNomeNormalizado() {
        NomesNormalizados nomeNormalizado = new NomesNormalizados("EXTRA", "EXTRA SUPERMERCADOS");
        NomesNormalizados saved = repository.save(nomeNormalizado);
        
        saved.setNomeRegularizado("EXTRA HYPERMARKET");
        NomesNormalizados updated = repository.save(saved);
        
        assertThat(updated.getNomeRegularizado()).isEqualTo("EXTRA HYPERMARKET");
    }

    @Test
    void deveDeletarUmNomeNormalizado() {
        NomesNormalizados nomeNormalizado = new NomesNormalizados("EXTRA", "EXTRA SUPERMERCADOS");
        NomesNormalizados saved = repository.save(nomeNormalizado);
        
        repository.deleteById(saved.getId());
        
        Optional<NomesNormalizados> found = repository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
} 