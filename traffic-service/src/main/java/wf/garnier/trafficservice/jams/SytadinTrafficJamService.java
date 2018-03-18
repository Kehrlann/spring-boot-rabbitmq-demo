package wf.garnier.trafficservice.jams;

import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SytadinTrafficJamService implements ITrafficJamService {

    private String sytadinUrl;

    public SytadinTrafficJamService(String url) {
        sytadinUrl = url;
    }

    @Override
    public Integer getTotalKilometers() {
        RestTemplate client = new RestTemplate();

        String sytadinResponse = client.getForObject(sytadinUrl, String.class);
        Pattern pattern = Pattern.compile("alt=\"(\\d+) km\"");

        Matcher matcher = pattern.matcher(sytadinResponse);

        if(matcher.find()){
            return Integer.parseInt(matcher.group(1));
        }

        return null;
    }
}
