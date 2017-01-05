package pl.allegro.umk.crazybill.api.dto;

import java.util.List;

public class PositionDto {

    private final String name;
    private final double price;
    private final List<PersonDto> persons;

    public PositionDto(String name, double price, List<PersonDto> persons) {
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

    public List<PersonDto> getPersons() {
        return persons;
    }
}
