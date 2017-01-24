package pl.allegro.umk.crazybill.domain;

import pl.allegro.umk.crazybill.api.dto.PersonDto;
import pl.allegro.umk.crazybill.api.dto.PositionDto;

import java.util.ArrayList;
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

    public static BillPosition fromDto(PositionDto positionDto) {
        List<String> persons = new ArrayList<>();
        for (PersonDto personDto: positionDto.getPersons()){
            persons.add(personDto.getName());
        }
        return new BillPosition(positionDto.getName(), positionDto.getPrice(), persons);
    }

    public PositionDto toDto() {
        List<PersonDto> personDtos = new ArrayList<>();
        for (String person: persons) {
            personDtos.add(new PersonDto(person));
        }
        return new PositionDto(name, price, personDtos);
    }
}
