package com.frontech.springofemails.collector.rest;

import akka.actor.ActorRef;
import com.frontech.springofemails.collector.com.frontech.springofemails.repository.EmailRepository;
import com.frontech.springofemails.collector.exception.NotFoundException;
import com.frontech.springofemails.collector.messages.Dataset;
import com.frontech.springofemails.data.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private ActorRef masterActor;

    @Autowired
    private EmailRepository emailRepository;

    @PostMapping(value = "/emails",
            consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void saveEmails(@RequestBody Dataset dataset) {
        logger.info("emails#POST is requested with {}", dataset);
        masterActor.tell(dataset, ActorRef.noSender());
    }

    @GetMapping(value = "/emails/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Email getEmail(@PathVariable String email) {
        logger.info("emails/{email}#GET is requested with {}", email);
        return emailRepository.findById(email).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = "/emails")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Email> getAllEmails() {
        logger.info("emails#GET is requested");
        return emailRepository.findAll();
    }
}
