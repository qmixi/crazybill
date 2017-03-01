package pl.allegro.umk.crazybill.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PositionDto {

    private final String name;
    private final double price;
    private final List<PersonDto> persons;

    @JsonCreator
    public PositionDto(
            @JsonProperty("name") String name,
            @JsonProperty("price") double price,
            @JsonProperty("persons") List<PersonDto> persons) {
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
