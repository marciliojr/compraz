package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.TipoCupom;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class EstabelecimentoServiceTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @Mock
    private CompraService compraService;

    private EstabelecimentoService estabelecimentoService;

    private Estabelecimento estabelecimento;

    @BeforeEach
    void setUp() {
        estabelecimentoService = new EstabelecimentoService(estabelecimentoRepository, compraService);

        estabelecimento = new Estabelecimento();
        estabelecimento.setId(1L);
        estabelecimento.setNomeEstabelecimento("Mercado Teste");
        estabelecimento.setTipoCupom(TipoCupom.MERCADO);
    }

    @Test
    void deveListarTodosEstabelecimentos() {
        List<Estabelecimento> estabelecimentos = Collections.singletonList(estabelecimento);
        when(estabelecimentoRepository.findAll()).thenReturn(estabelecimentos);

        List<Estabelecimento> resultado = estabelecimentoService.listarTodos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNomeEstabelecimento()).isEqualTo("Mercado Teste");
        verify(estabelecimentoRepository).findAll();
    }

    @Test
    void deveRemoverEstabelecimento() {
        when(compraService.existeComprasPorEstabelecimento(1L))
                .thenReturn(false);

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

    @Test
    void deveVerificarSeExistemComprasPorEstabelecimento() {
        when(compraService.existeComprasPorEstabelecimento(1L)).thenReturn(true);

        boolean resultado = estabelecimentoService.existeComprasPorEstabelecimento(1L);

        assertThat(resultado).isTrue();
        verify(compraService).existeComprasPorEstabelecimento(1L);
    }

    @Test
    void deveAtualizarEstabelecimento() {
        Estabelecimento estabelecimentoAtualizado = new Estabelecimento();
        estabelecimentoAtualizado.setId(1L);
        estabelecimentoAtualizado.setNomeEstabelecimento("Mercado Atualizado");
        estabelecimentoAtualizado.setTipoCupom(TipoCupom.MERCADO);

        when(estabelecimentoRepository.findById(1L)).thenReturn(Optional.of(estabelecimento));
        when(estabelecimentoRepository.save(any(Estabelecimento.class))).thenReturn(estabelecimentoAtualizado);

        Estabelecimento resultado = estabelecimentoService.atualizarEstabelecimento(1L, estabelecimentoAtualizado);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNomeEstabelecimento()).isEqualTo("Mercado Atualizado");
        verify(estabelecimentoRepository).findById(1L);
        verify(estabelecimentoRepository).save(any(Estabelecimento.class));
    }

    @Test
    void deveLancarExcecaoQuandoEstabelecimentoNaoExiste() {
        Estabelecimento estabelecimentoAtualizado = new Estabelecimento();
        estabelecimentoAtualizado.setId(1L);
        estabelecimentoAtualizado.setNomeEstabelecimento("Mercado Atualizado");
        estabelecimentoAtualizado.setTipoCupom(TipoCupom.MERCADO);

        when(estabelecimentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                estabelecimentoService.atualizarEstabelecimento(1L, estabelecimentoAtualizado));

        verify(estabelecimentoRepository).findById(1L);
        verify(estabelecimentoRepository, never()).save(any(Estabelecimento.class));
    }
}