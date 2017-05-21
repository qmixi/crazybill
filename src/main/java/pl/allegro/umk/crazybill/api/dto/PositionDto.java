package pl.allegro.umk.crazybill.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionDto that = (PositionDto) o;
        return Double.compare(that.price, price) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, persons);
    }

    @Override
    public String toString() {
        return "PositionDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", persons=" + persons +
                '}';
    }
}
