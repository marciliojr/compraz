package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstabelecimentoServiceTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private EstabelecimentoService estabelecimentoService;

    private Estabelecimento estabelecimento;

    @BeforeEach
    void setUp() {
        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);
    }

    @Test
    void deveListarTodosEstabelecimentos() {

        List<Estabelecimento> estabelecimentos = Collections.singletonList(estabelecimento);
        when(estabelecimentoRepository.findAllEstabelecimento()).thenReturn(estabelecimentos);

        List<Estabelecimento> resultado = estabelecimentoService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(estabelecimentoRepository).findAllEstabelecimento();
    }

    @Test
    void deveRemoverEstabelecimento() {

        estabelecimentoService.removerEstabelecimento(1L);

        verify(estabelecimentoRepository).deleteById(anyLong());
    }

    @Test
    void deveObterEstabelecimentoPorNome() {

        when(estabelecimentoRepository.findByNomeEstabelecimento("Mercado Teste"))
                .thenReturn(Optional.of(estabelecimento));

        Optional<Estabelecimento> resultado = estabelecimentoService.obterEstabelecimento("Mercado Teste");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(estabelecimentoRepository).findByNomeEstabelecimento("Mercado Teste");
    }

    @Test
    void deveRetornarVazioQuandoNaoEncontrarEstabelecimento() {

        when(estabelecimentoRepository.findByNomeEstabelecimento("Estabelecimento Inexistente"))
                .thenReturn(Optional.empty());

        Optional<Estabelecimento> resultado = estabelecimentoService.obterEstabelecimento("Estabelecimento Inexistente");

        assertThat(resultado).isEmpty();
        verify(estabelecimentoRepository).findByNomeEstabelecimento("Estabelecimento Inexistente");
    }
} 