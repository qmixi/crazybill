package pl.allegro.umk.crazybill;

import java.util.List;

/**
 * Created by jakub.westfalewski on 22/11/16.
 */
public class BillPosition {
    private final String name;
    private final double price;
    private final List<String> persons;

    public BillPosition(String name, double price, List<String> persons) {

        this.name = name;
        this.price = price;
        this.persons = persons;
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
