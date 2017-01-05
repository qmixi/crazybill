package pl.allegro.umk.crazybill.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BillDto {

    private final String id;
    private final String name;
    private final List<PositionDto> positions;

    @JsonCreator
    public BillDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("positions") List<PositionDto> positions
    ) {
        this.id = id;
        this.name = name;
        this.positions = positions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillDto billDto = (BillDto) o;

        if (id != null ? !id.equals(billDto.id) : billDto.id != null) return false;
        if (name != null ? !name.equals(billDto.name) : billDto.name != null) return false;
        return positions != null ? positions.equals(billDto.positions) : billDto.positions == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (positions != null ? positions.hashCode() : 0);
        return result;
    }
}
