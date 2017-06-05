package pl.allegro.umk.crazybill.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class BillDto {

    private final String id;
    private final String name;
    private final String email;
    private final List<PositionDto> positions;

    @JsonCreator
    public BillDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("positions") List<PositionDto> positions
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.positions = positions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillDto billDto = (BillDto) o;
        return Objects.equals(id, billDto.id) &&
                Objects.equals(name, billDto.name) &&
                Objects.equals(positions, billDto.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, positions);
    }

    @Override
    public String toString() {
        return String.format("BillDto{id='%s', name='%s', positions=%s}", id, name, positions);
    }
}
