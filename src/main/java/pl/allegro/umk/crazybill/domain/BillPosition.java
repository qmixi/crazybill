package pl.allegro.umk.crazybill.domain;

import java.util.Collections;
import java.util.List;

public class BillPosition {
    private final String name;
    private final double price;
    private final List<String> persons;

    public BillPosition(String name, double price, List<String> persons) {
        this.name = name;
        this.price = price;
        this.persons = Collections.unmodifiableList(persons);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getPersons() {
        return persons;
    }
}
