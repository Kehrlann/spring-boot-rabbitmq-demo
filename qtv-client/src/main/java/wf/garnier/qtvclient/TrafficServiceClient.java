package wf.garnier.qtvclient;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrafficServiceClient {

    @Autowired
    private RabbitTemplate client;

    public void sendQtv(QTV qtv) {

        client.setMessageConverter(new Jackson2JsonMessageConverter());
        client.convertAndSend("rabbitmq-demo", qtv);
    }
}
