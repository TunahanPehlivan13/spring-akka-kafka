package com.frontech.springofemails.recorder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    /*
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaConsumer receiver;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, BOOT_TOPIC);
    */
    @Test
    public void testReceive() throws Exception {
        //kafkaTemplate.send(BOOT_TOPIC, "Hello Boot!");

        //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        //assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }

}
