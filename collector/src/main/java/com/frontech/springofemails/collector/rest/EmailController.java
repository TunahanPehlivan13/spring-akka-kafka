package com.frontech.springofemails.collector.rest;

import akka.actor.ActorRef;
import com.frontech.springofemails.collector.messages.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private ActorRef masterActor;

    @PostMapping(value = "/emails",
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void saveEmails(@RequestBody Dataset dataset) {
        logger.info("emails#POST is requested with {]", dataset);
        masterActor.tell(dataset, ActorRef.noSender());
    }
}
