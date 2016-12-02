package pl.allegro.umk.crazybill;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BillCalculateTests {

    @Test
    public void should_calculate_bill() {
        // given
        List<String> pizzaPersons = new ArrayList();
        pizzaPersons.add("Jarek");

        List<BillPosition> positions = new ArrayList();
        positions.add(new BillPosition("Pizza", 20.0, pizzaPersons));

        Bill bill = new Bill(positions);

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(1);
        assertThat(result.getPerson("Jarek")).isEqualTo(20.0);
    }

    @Test
    public void should_calculate_bill_with_many_positions() throws Exception {
        // given
        List<String> kebabPersons = new ArrayList();
        kebabPersons.add("Jarek");
        kebabPersons.add("Michal");

        List<BillPosition> positions = new ArrayList<>();
        positions.add(new BillPosition("Kebab", 22.0, kebabPersons));

        List<String> colaPersons = new ArrayList<>();
        colaPersons.add("Michal");

        positions.add(new BillPosition("Cola", 4, colaPersons));

        Bill bill = new Bill(positions);

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(2);
        assertThat(result.getPerson("Jarek")).isEqualTo(11);
        assertThat(result.getPerson("Michal")).isEqualTo(15);
    }

    @Test
    public void should_calculate_bill_with_builder() throws Exception {
        Bill bill = Bill.builder()
                .paidFor("Kebab", 22.0).by("Michal", "Jarek")
                .paidFor("Cola", 4.00).by("Michal")
                .build();

        // when
        BillResult result = new BillCalculator().calculate(bill);

        // then
        assertThat(result.getPersonsCount()).isEqualTo(2);
        assertThat(result.getPerson("Jarek")).isEqualTo(11);
        assertThat(result.getPerson("Michal")).isEqualTo(15);
    }
}
