package wf.garnier.trafficservice.qtv;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QTVReceiver {

    @Autowired
    private QTVRepository repository;

    Logger logger = LoggerFactory.getLogger(QTVReceiver.class);

    @RabbitListener(queues = {"rabbitmq-demo"}, containerFactory = "trafficFactory")
    public void receiveQtv(QTV qtv) {
        logger.info("Received : " + qtv.getLocalisation() + " - " + qtv.getVitesse());
        repository.save(qtv);
    }
}
