package com.frontech.springofemails.recorder.consumer;

import com.datastax.driver.core.utils.UUIDs;
import com.frontech.springofemails.data.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.WriteResult;
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
    private final long FIVE_MINUTES = 20 * 1000;
    private CassandraBatchOperations batchOperations;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @PostConstruct
    public void init() {
        batchOperations = cassandraTemplate.batchOps();

        new Timer().schedule(new TimerTask() {
            public void run() {
                WriteResult result = batchOperations.execute();
                if (result.wasApplied()) {
                    logger.info("Bulk data is executed");
                } else {
                    logger.error("There was something wrong!");
                }
                batchOperations = cassandraTemplate.batchOps();
            }
        }, 0, FIVE_MINUTES);
    }

    @KafkaListener(topics = "emails", groupId = "frontech")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        logger.info("Received payload='{}'", consumerRecord.toString());

        batchOperations.insert(new Email(UUIDs.timeBased(), consumerRecord.value()));

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
