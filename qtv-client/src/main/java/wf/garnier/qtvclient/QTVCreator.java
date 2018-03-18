package wf.garnier.qtvclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class QTVCreator {

    Logger logger = LoggerFactory.getLogger(QTVCreator.class);

    @Autowired
    private TrafficServiceClient client;

    @Scheduled(fixedRate = 3000)
    public void createQtv() {
        Random random = new Random();
        QTV qtv = new QTV("nanterre", random.nextInt(10), random.nextInt(5), random.nextInt(90));
        logger.info("QTV created - nanterre");

        client.sendQtv(qtv);
    }
}
