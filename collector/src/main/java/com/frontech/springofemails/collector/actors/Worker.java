package com.frontech.springofemails.collector.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.frontech.springofemails.collector.messages.Dataset;
import com.frontech.springofemails.collector.service.EmailPublisher;
import com.frontech.springofemails.collector.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope("prototype")
public class Worker extends UntypedActor {

    private final LoggingAdapter log = Logging
            .getLogger(getContext().system(), "Master");

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
            log.info("{} will be processed...", dataset);

            dataset.getEmail().stream()
                    .filter(e -> emailValidator.validate(e))
                    .forEach(e -> {
                        emailPublisher.send("emails", e);
                    });

            dataset.getUrl()
                    .parallelStream()
                    .forEach(url -> {
                        Dataset requestedDataset = sendRequest(url);
                        log.info("{} is requested.", url);
                        masterActor.tell(requestedDataset, ActorRef.noSender());
                    });
        }
    }

    private Dataset sendRequest(String url) {
        ResponseEntity<Dataset> dataset = new RestTemplate().getForEntity(url, Dataset.class);
        return dataset.getBody();
    }
}