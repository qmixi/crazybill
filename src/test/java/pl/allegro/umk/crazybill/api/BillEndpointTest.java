package pl.allegro.umk.crazybill.api;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.allegro.umk.crazybill.CrazybillApplicationTests;
import pl.allegro.umk.crazybill.api.dto.BillDto;

import java.net.URI;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class BillEndpointTest extends CrazybillApplicationTests {

    @Test
    public void should_return_bill_by_id() {
        //given
        BillDto bill = new BillDto(null, "bill name", new ArrayList<>());
        URI location = restTemplate.postForLocation("http://localhost:" + port + "/bills", bill);
        String billId = location.getPath().replace("/bills/", "");
        BillDto expectedBill = new BillDto(billId, "bill name", new ArrayList<>());

        //when
        ResponseEntity<BillDto> entity = restTemplate.getForEntity("http://localhost:"+port+"/bills/" + billId, BillDto.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(expectedBill);
    }

    @Test
    public void should_not_found_not_existing_bill() {
        //when:
        ResponseEntity<BillDto> entity = restTemplate.getForEntity("http://localhost:"+port+"/bills/dummy", BillDto.class);

        //then:
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_create_bill() {
        //given
        BillDto bill = new BillDto(null, "test name", new ArrayList<>());

        //then
        ResponseEntity<Void> entity = restTemplate.postForEntity("http://localhost:" + port + "/bills", bill, Void.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void should_delete_bill() {
        //given
        BillDto bill = new BillDto(null, "bill name", new ArrayList<>());
        URI location = restTemplate.postForLocation("http://localhost:" + port + "/bills", bill);

        //when
        restTemplate.delete("http://localhost:"+port+location.getPath());

        //then
        ResponseEntity<BillDto> entity = restTemplate.getForEntity("http://localhost:"+port+location.getPath(), BillDto.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
