package com.frontech.springofemails.collector;

import com.frontech.springofemails.collector.rest.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private EmailService emailService;

    @Test
    public void contextLoads() {
        assertNotNull(emailService);
    }
}
