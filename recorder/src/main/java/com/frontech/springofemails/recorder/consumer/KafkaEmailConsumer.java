package com.frontech.springofemails.recorder.consumer;

import com.frontech.springofemails.recorder.data.Email;
import com.frontech.springofemails.recorder.repository.EmailRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaEmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEmailConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private EmailRepository emailRepository;

    @KafkaListener(topics = "emails", groupId = "frontech")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        logger.info("Received payload='{}'", consumerRecord.toString());

        emailRepository.save(new Email(consumerRecord.value(), 1));
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
