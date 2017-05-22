package pl.allegro.umk.crazybill;

import org.springframework.stereotype.Service;
import pl.allegro.umk.crazybill.domain.Bill;
import pl.allegro.umk.crazybill.domain.BillPosition;
import pl.allegro.umk.crazybill.domain.BillResult;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class BillCalculator {
    public BillResult calculate(Bill bill) {

        Map<String, Double> resultsPerPerson = new LinkedHashMap<>();

        for (BillPosition position : bill.getPositions()) {
            double pricePerPerson = position.getPrice() / position.getPersons().size();

            for (String person : position.getPersons()) {
                double priceForPerson = calculatePriceForPerson(resultsPerPerson, pricePerPerson, person);
                resultsPerPerson.put(person, priceForPerson);
            }
        }

        return new BillResult(resultsPerPerson);
    }

    private double calculatePriceForPerson(Map<String, Double> resultsPerPerson, double pricePerPerson, String person) {
        double price = pricePerPerson;

        if (resultsPerPerson.containsKey(person)) {
            price += resultsPerPerson.get(person);
        }
        return price;
    }
}
