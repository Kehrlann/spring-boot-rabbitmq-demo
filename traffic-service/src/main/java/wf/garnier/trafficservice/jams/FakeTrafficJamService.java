package wf.garnier.trafficservice.jams;

public class FakeTrafficJamService implements ITrafficJamService {

    @Override
    public Integer getTotalKilometers() {
        return 152;
    }
}
