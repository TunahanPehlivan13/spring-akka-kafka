package com.frontech.springofemails.collector.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.frontech.springofemails.collector.messages.Dataset;
import com.frontech.springofemails.collector.service.EmailPublisher;
import com.frontech.springofemails.collector.validator.EmailValidator;
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

    @Autowired
    private EmailValidator emailValidator;

    @Override
    public void onReceive(Object message) {

        if (message instanceof Dataset) {
            Dataset dataset = (Dataset) message;
            dataset.getEmail().stream()
                    .filter(e -> emailValidator.validate(e))
                    .forEach(e -> {
                        emailPublisher.send("emails", e);
                    });

            dataset.getUrl().forEach(url -> { // TODO send request
                /*Dataset requestedDataset = httpService.sendRequest(url);
                log.info("{} is requested", url);
                masterActor.tell(requestedDataset, ActorRef.noSender());*/
            });
        }
    }
}