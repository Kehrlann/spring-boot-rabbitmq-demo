package wf.garnier.trafficservice;


import wf.garnier.trafficservice.jams.ITrafficJamService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrafficControllerSpringTest {

    @LocalServerPort
    public int serverPort;

    @MockBean
    private ITrafficJamService service;

    @Test
    public void itSaysHi() {
        RestTemplate client = new RestTemplate();

        String response = client.getForObject("http://localhost:" + serverPort + "/hello", String.class);

        Assertions.assertThat(response).containsIgnoringCase("hello");
    }

    @Test
    public void itGetsTheTotalKilometers() {

        Mockito.when(service.getTotalKilometers()).thenReturn(12);
        RestTemplate client = new RestTemplate();

        String response = client.getForObject("http://localhost:" + serverPort + "/bouchons", String.class);

        Assertions.assertThat(response).containsIgnoringCase("12");
    }

    @Test
    public void itCallsTheService() {
        RestTemplate client = new RestTemplate();

        String response = client.getForObject("http://localhost:" + serverPort + "/bouchons", String.class);
        Mockito.verify(service, Mockito.times(1)).getTotalKilometers();
    }
}
