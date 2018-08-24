package com.frontech.springofemails.recorder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS emails_keyspace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE emails_keyspace;";

    /*
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaConsumer receiver;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, BOOT_TOPIC);
    */
    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException, IOException, TTransportException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        logger.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        logger.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Test
    public void testReceive() throws Exception {
        //kafkaTemplate.send(BOOT_TOPIC, "Hello Boot!");

        //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        //assertThat(receiver.getLatch().getCount()).isEqualTo(0);
    }

}
