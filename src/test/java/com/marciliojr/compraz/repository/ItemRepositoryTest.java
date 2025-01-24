package com.marciliojr.compraz.repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static com.marciliojr.compraz.mock.EntidadesMockBuilder.criarCompra;
import static com.marciliojr.compraz.mock.EntidadesMockBuilder.criarEstabelecimento;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Anotação específica para testar repositórios JPA
class ItemRepositoryTest {

    public static final String PRODUTO_TESTE = "Produto Teste";
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;


    @Test
    void deveSalvarERecuperarItem() {

        Estabelecimento estabelecimento = criarEstabelecimento();
        estabelecimentoRepository.save(estabelecimento);

        Compra compra = criarCompra(estabelecimento);
        compraRepository.save(compra);

        Item item = new Item();
        item.setCompra(compra);
        item.setValorUnitario(new BigDecimal("9.99"));
        item.setNome(PRODUTO_TESTE);
        item.setQuantidade(BigDecimal.TEN);
        item.setUnidade("kg");

        Item salvo = itemRepository.save(item);

        List<Item> itens = itemRepository.findAll();

        assertThat(itens).isNotEmpty();
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo(PRODUTO_TESTE);
    }
}
