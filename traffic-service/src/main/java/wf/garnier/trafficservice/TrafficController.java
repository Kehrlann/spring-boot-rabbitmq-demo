package wf.garnier.trafficservice;


import wf.garnier.trafficservice.jams.ITrafficJamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficController {

    @Autowired
    private ITrafficJamService service;

    @Value("${traffic.jam.message:Bouchons en cours: }")
    private String jamMessage;

    public TrafficController() {
    }

    public TrafficController(ITrafficJamService service) {
        this.service = service;
    }


    @GetMapping("/hello")
    public String sayHi(@RequestParam(value = "name", defaultValue = "world") String name) {
        return "Hello, " + name + " !";
    }

    @GetMapping("/bouchons")
    public String getTotalKilometers() {
        Integer kms = service.getTotalKilometers();
        return jamMessage + kms + "km.";
    }
}
