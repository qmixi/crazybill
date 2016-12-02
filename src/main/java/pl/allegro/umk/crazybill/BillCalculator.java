package pl.allegro.umk.crazybill;

import java.util.HashMap;

public class BillCalculator {
    public BillResult calculate(Bill bill) {

        HashMap<String, Double> resultsPerPerson = new HashMap<>();

        for (BillPosition position : bill.getPositions()) {
            double pricePerPerson = position.getPrice() / position.getPersons().size();

            for (String person : position.getPersons()) {
                if (resultsPerPerson.containsKey(person)) {
                    resultsPerPerson.put(person, pricePerPerson + resultsPerPerson.get(person));
                } else {
                    resultsPerPerson.put(person, pricePerPerson);
                }
            }
        }

        return new BillResult(resultsPerPerson);
    }
}
