package pl.allegro.umk.crazybill.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.allegro.umk.crazybill.CrazybillApplication;
import pl.allegro.umk.crazybill.CrazybillApplicationTests;
import pl.allegro.umk.crazybill.api.dto.BillDto;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class BillEndpointTest extends CrazybillApplicationTests {

    @Test
    public void should_return_bill_by_id() {
        //given
        String billId = "billId";
        BillDto expectedBill = new BillDto(billId, "bill name", new ArrayList<>());

        //when
        ResponseEntity<BillDto> entity = restTemplate.getForEntity("http://localhost:"+port+"/bills/" + billId, BillDto.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(expectedBill);
    }

}
