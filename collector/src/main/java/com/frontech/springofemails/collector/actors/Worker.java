package com.frontech.springofemails.collector.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.frontech.springofemails.collector.messages.Dataset;
import com.frontech.springofemails.collector.service.EmailPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Worker extends UntypedActor {

    @Autowired
    private ActorRef masterActor;

    @Autowired
    private EmailPublisher emailPublisher;

    @Override
    public void onReceive(Object message) {

        if (message instanceof Dataset) {
            Dataset dataset = (Dataset) message;
            dataset.getEmail().forEach(e -> {
                emailPublisher.send("emails", e);
            });

            dataset.getUrl().forEach(url -> { // TODO can not send request
                /*Dataset requestedDataset = httpService.sendRequest(url);
                log.info("{} is requested", url);
                masterActor.tell(requestedDataset, ActorRef.noSender());*/
            });
        }
    }
}