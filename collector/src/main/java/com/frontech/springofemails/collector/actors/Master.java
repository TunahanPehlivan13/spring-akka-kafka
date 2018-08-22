package com.frontech.springofemails.collector.actors;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.ActorRefRoutee;
import akka.routing.RandomRoutingLogic;
import akka.routing.Routee;
import akka.routing.Router;
import com.frontech.springofemails.collector.messages.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Master extends UntypedActor {

    private final LoggingAdapter log = Logging
            .getLogger(getContext().system(), "Master");

    private Router router;

    @Autowired
    private ActorRef workerActor;

    @Override
    public void preStart() throws Exception {

        log.info("Starting up");

        List<Routee> routees = new ArrayList<>();
        for (int nrOfActor = 0; nrOfActor < 10; nrOfActor++) {
            getContext().watch(workerActor);
            routees.add(new ActorRefRoutee(workerActor));
        }
        router = new Router(new RandomRoutingLogic(), routees);
        super.preStart();
    }

    @Override
    public void onReceive(Object message) {
        log.info(message.toString());
        if (message instanceof Dataset) {
            router.route(message, getSender());
        } else if (message instanceof Terminated) {
            router = router.removeRoutee(((Terminated) message).actor());
            getContext().watch(workerActor);
            router = router.addRoutee(new ActorRefRoutee(workerActor));
            log.error("Unable to handle message {}", message);
        } else {
            log.error("Unable to handle message {}", message);
        }
    }

    @Override
    public void postStop() throws Exception {
        log.info("Shutting down");
        super.postStop();
    }
}