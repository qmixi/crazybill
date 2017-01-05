package pl.allegro.umk.crazybill.api.dto;

public class PersonDto {

    private final String name;

    public PersonDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
