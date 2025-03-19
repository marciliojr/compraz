package com.marciliojr.compraz.service;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class PDFDadosServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private PDFDadosService pdfDadosService;

    private Estabelecimento estabelecimento;
    private Compra compra;

    @BeforeEach
    void setUp() {
//        estabelecimento = new Estabelecimento(1L, "Supermercado ABC");
//        compra = new Compra();
//        compra.setDataCompra(LocalDate.of(2024, 1, 25));
//        compra.setEstabelecimento(estabelecimento);
    }

    @Test
    void processarDadosEPersistir_DevePersistirCompraComItens() {
//        String textoPDF = """
//            Arroz Branco (Código: 12345) Qtde.: 2.00 UN: Kg Vl. Unit.: 10.00 Vl. Total 20.00
//            Feijão Preto (Código: 67890) Qtde.: 1.00 UN: Kg Vl. Unit.: 15.00 Vl. Total 15.00
//            """;
//
//        when(estabelecimentoRepository.findByNomeEstabelecimento("Supermercado ABC"))
//                .thenReturn(Optional.of(estabelecimento));
//        when(compraRepository.save(any(Compra.class))).thenReturn(compra);
//
//        pdfDadosService.processarDadosEPersistir(textoPDF, "Supermercado ABC", LocalDate.of(2024, 1, 25));
//
//        verify(compraRepository, times(1)).save(any(Compra.class));
    }

}
