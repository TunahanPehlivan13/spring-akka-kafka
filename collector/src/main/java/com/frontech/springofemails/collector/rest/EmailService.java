package com.frontech.springofemails.collector.rest;

import akka.actor.ActorRef;
import com.frontech.springofemails.collector.dto.EmailDTO;
import com.frontech.springofemails.collector.exception.NotFoundException;
import com.frontech.springofemails.collector.messages.Dataset;
import com.frontech.springofemails.collector.repository.EmailRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

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
    public EmailDTO getEmail(@PathVariable String email) {
        logger.info("emails/{email}#GET is requested with {}", email);

        int count = emailRepository.findByText(email).size();
        boolean isExist = count > 0;

        if (isExist) {
            return new EmailDTO(email, Long.valueOf(count));
        }
        throw new NotFoundException();
    }

    @GetMapping(value = "/emails")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailDTO> getAllEmails() {
        logger.info("emails#GET is requested");

        List<EmailDTO> emails = Lists.newArrayList();
        Map<String, Long> emailMap = emailRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> e.getText(),
                        Collectors.counting()));

        emailMap.forEach((email, count) -> {
            emails.add(new EmailDTO(email, count));
        });
        return emails;
    }

    @GetMapping(value = "/dummy-emails")
    @ResponseStatus(HttpStatus.OK)
    public Dataset getDummyEmails() {
        logger.info("emails#GET is requested");
        Dataset dataset = new Dataset();
        dataset.getEmail().add("tuna@hotmail.com");
        return dataset;
    }
}
