package wf.garnier.trafficservice;

import wf.garnier.trafficservice.jams.FakeTrafficJamService;
import wf.garnier.trafficservice.jams.ITrafficJamService;
import wf.garnier.trafficservice.jams.SytadinTrafficJamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrafficServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrafficServiceApplication.class, args);
    }

    @Value("${traffic.jam.sytadin:}")
    private String sytadinurl;

    Logger logger = LoggerFactory.getLogger(TrafficServiceApplication.class);

    @Bean
    public ITrafficJamService service() {

        if (sytadinurl != null && !sytadinurl.isEmpty()) {
            logger.info("Found sytadin url : " + sytadinurl);
            return new SytadinTrafficJamService(sytadinurl);
        } else {
            logger.info("No sytadin URL, used a fake service !");
            return new FakeTrafficJamService();
        }
    }

    @Bean
    public SimpleRabbitListenerContainerFactory trafficFactory(ConnectionFactory factory,
                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer)
    {
        SimpleRabbitListenerContainerFactory result = new SimpleRabbitListenerContainerFactory();
        configurer.configure(result, factory);
        result.setMessageConverter(new Jackson2JsonMessageConverter());

        return result;
    }
}
