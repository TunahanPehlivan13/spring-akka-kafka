package com.frontech.springofemails.recorder.consumer;

import com.datastax.driver.core.utils.UUIDs;
import com.frontech.springofemails.data.Email;
import com.frontech.springofemails.recorder.wrapper.CassandraBatchWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

@Component
public class KafkaEmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEmailConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);
    private final long FIVE_MINUTES = 5 * 60 * 1000;

    @Autowired
    private CassandraBatchWrapper cassandraBatchWrapper;

    @PostConstruct
    public void init() {

        new Timer().schedule(new TimerTask() {
            public void run() {
                cassandraBatchWrapper.execute();
            }
        }, 0, FIVE_MINUTES);
    }

    @KafkaListener(topics = "emails", groupId = "frontech")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        logger.info("Received payload='{}'", consumerRecord.toString());

        cassandraBatchWrapper.insert(new Email(UUIDs.timeBased(), consumerRecord.value()));

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
