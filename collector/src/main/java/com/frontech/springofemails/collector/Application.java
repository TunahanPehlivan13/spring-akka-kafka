package com.frontech.springofemails.collector;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.frontech.springofemails.collector.extension.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.frontech.springofemails")
public class Application {

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SpringExtension springExtension;

    @Bean(name = "masterActor")
    public ActorRef master() {
        return actorSystem.actorOf(springExtension.props("master"));
    }

    @Bean(name = "workerActor")
    public ActorRef worker() {
        return actorSystem.actorOf(springExtension.props("worker"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
