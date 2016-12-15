package pl.allegro.umk.crazybill.domain;

import java.util.Map;

public class BillResult {
    private final Map<String, Double> resultsPerPerson;

    public BillResult(Map<String, Double> resultsPerPerson) {
        this.resultsPerPerson = resultsPerPerson;
    }

    public Integer getPersonsCount() {
        return resultsPerPerson.size();
    }

    public Double getPriceForPerson(String name) {
        return resultsPerPerson.get(name);
    }
}
