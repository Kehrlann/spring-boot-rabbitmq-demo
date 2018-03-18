package wf.garnier.trafficservice;

import wf.garnier.trafficservice.jams.FakeTrafficJamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TrafficController.class, FakeTrafficJamService.class})
public class TrafficControllerWebMvcTest {

    @Autowired
    public MockMvc mvc;

    @Test
    public void itSaysHi() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello?name=Daniel"))
                .andExpect(MockMvcResultMatchers.content().string("Hello, Daniel !"));
    }
}
