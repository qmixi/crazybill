package pl.allegro.umk.crazybill;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(
		classes = {CrazybillApplication.class},
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class CrazybillApplicationTests {

	public TestRestTemplate restTemplate = new TestRestTemplate();

	@Value("${local.server.port}")
	public int port;

}
