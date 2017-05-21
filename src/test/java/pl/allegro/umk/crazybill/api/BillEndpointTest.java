package pl.allegro.umk.crazybill.api;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.allegro.umk.crazybill.CrazybillApplicationTests;
import pl.allegro.umk.crazybill.api.dto.BillDto;
import pl.allegro.umk.crazybill.api.dto.PersonDto;
import pl.allegro.umk.crazybill.api.dto.PositionDto;
import sun.jvm.hotspot.runtime.Threads;

import javax.mail.Message;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BillEndpointTest extends CrazybillApplicationTests {

    private GreenMail testSmtp;

    @Before
    public void testSmtpInit() {
        ServerSetup setup = new ServerSetup(65438, "localhost", "smtp");
        testSmtp = new GreenMail(setup);
        testSmtp.start();
    }

    @Test
    public void should_return_bill_by_id() throws Exception {
        //given
        BillDto bill = new BillDto(null, "bill name", Collections.singletonList(new PositionDto("pizza", 10, Collections.singletonList(new PersonDto("user@dummy.pl")))));
        URI location = restTemplate.postForLocation("http://localhost:" + port + "/bills", bill);
        String billId = location.getPath().replace("/bills/", "");
        BillDto expectedBill = new BillDto(billId, "bill name", Collections.singletonList(new PositionDto("pizza", 10, Collections.singletonList(new PersonDto("user@dummy.pl")))));

        //when
        ResponseEntity<BillDto> entity = restTemplate.getForEntity("http://localhost:"+port+"/bills/" + billId, BillDto.class);

        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(expectedBill);

        assertTrue(testSmtp.waitForIncomingEmail(5000, 1));
        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("New payment", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertThat(body).startsWith("Hello,");
        assertThat(body).contains("You had a pleasure make a shopping/eating together with friends.");
        assertThat(body).contains("You have made shopping/eating for amount: 10.000000 PLN. The bill name is: bill name. Please give back money to your friend.");
        assertThat(body).endsWith("Yours Crazy Bill");
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

    @After
    public void cleanup(){
        testSmtp.stop();
    }
}
