package wf.garnier.trafficservice;

import wf.garnier.trafficservice.jams.ITrafficJamService;
import wf.garnier.trafficservice.jams.FakeTrafficJamService;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class TrafficControllerTest {


    @Test
    public void itSaysHi() {
        TrafficController trafficController = new TrafficController();

        assertThat(trafficController.sayHi("toto")).containsIgnoringCase("hello");
    }

    @Test
    public void itGetsTheRightKilometers() {
        ITrafficJamService service = Mockito.mock(FakeTrafficJamService.class);
        Mockito.when(service.getTotalKilometers()).thenReturn(42);
        TrafficController trafficController = new TrafficController(service);

        assertThat(trafficController.getTotalKilometers()).containsIgnoringCase("42");
    }

}