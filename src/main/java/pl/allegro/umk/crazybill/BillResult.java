package pl.allegro.umk.crazybill;

import java.util.HashMap;

/**
 * Created by jakub.westfalewski on 22/11/16.
 */
public class BillResult {
    private final HashMap<String, Double> resultsPerPerson;

    public BillResult(HashMap<String, Double> resultsPerPerson) {
        this.resultsPerPerson = resultsPerPerson;
    }

    public Integer getPersonsCount() {
        return resultsPerPerson.size();
    }

    public Double getPerson(String name) {
        return resultsPerPerson.get(name);
    }
}
