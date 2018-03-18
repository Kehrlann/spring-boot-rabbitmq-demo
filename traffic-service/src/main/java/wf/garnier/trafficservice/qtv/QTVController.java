package wf.garnier.trafficservice.qtv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class QTVController {

    @Autowired
    private QTVRepository repository;

    @GetMapping("/qtv")
    public Collection<QTV> getQtv(@RequestParam(value = "loc", defaultValue = "") String loc) {
        if(loc.isEmpty())
            return repository.findAll();
        else
            return repository.findByLocalisationContaining(loc);
    }

    @PostMapping("/qtv")
    public void createQTV(@RequestBody QTV qtv) {
        repository.save(qtv);
    }



}
