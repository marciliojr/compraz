package repository;

import com.marciliojr.compraz.model.Compra;
import com.marciliojr.compraz.model.Estabelecimento;
import com.marciliojr.compraz.model.Item;
import com.marciliojr.compraz.model.dto.ItemDTO;
import com.marciliojr.compraz.repository.CompraRepository;
import com.marciliojr.compraz.repository.EstabelecimentoRepository;
import com.marciliojr.compraz.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private Estabelecimento estabelecimento;
    private Compra compra;

    @BeforeEach
    void setup() {
        // Criando um estabelecimento
        estabelecimento = new Estabelecimento();
        estabelecimento.setNomeEstabelecimento("Mercado Central");
        estabelecimentoRepository.save(estabelecimento);

        // Criando uma compra
        compra = new Compra();
        compra.setDataCompra(LocalDate.of(2024, 1, 10));
        compra.setEstabelecimento(estabelecimento);
        compraRepository.save(compra);

        // Criando itens
        Item item1 = new Item();
        item1.setNome("Arroz");
        item1.setQuantidade(BigDecimal.valueOf(2));
        item1.setUnidade("kg");
        item1.setValorUnitario(new BigDecimal("10.00"));
        item1.setValorTotal(new BigDecimal("20.00"));
        item1.setCompra(compra);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setNome("Feijão");
        item2.setQuantidade(BigDecimal.ONE);
        item2.setUnidade("kg");
        item2.setValorUnitario(new BigDecimal("8.00"));
        item2.setValorTotal(new BigDecimal("8.00"));
        item2.setCompra(compra);
        itemRepository.save(item2);
    }

    @Test
    public void testFindAllItemsByEstabelecimentoAndPeriodo() {
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 1, 31);

        List<ItemDTO> itens = itemRepository.findAllItemsByEstabelecimentoAndPeriodo(
                "Mercado Central", dataInicio, dataFim);

        assertThat(itens).isNotEmpty();
        assertThat(itens).hasSize(2);
        assertThat(itens.get(0).getNome()).isEqualTo("Arroz");
        assertThat(itens.get(1).getNome()).isEqualTo("Feijão");
    }

    @Test
    public void testSumValorTotalByEstabelecimentoAndPeriodo() {
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 1, 31);

        BigDecimal total = itemRepository.sumValorTotalByEstabelecimentoAndPeriodo(
                "Mercado Central", dataInicio, dataFim);

        assertThat(total).isEqualByComparingTo(new BigDecimal("28.00"));
    }
}
