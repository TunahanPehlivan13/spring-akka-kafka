package com.frontech.springofemails.collector.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailValidatorTest {

    @Autowired
    private EmailValidator emailValidator;

    @Test
    public void shouldReturnTrueWhenValidEmailsAndWantedDomains() {
        String email = "mail@comeon.com";

        boolean result = emailValidator.validate(email);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenInvalidEmails() {
        String email = "mail%comeon.com";

        boolean result = emailValidator.validate(email);

        assertFalse(result);
    }

    @Test
    public void shouldReturnFalseWhenUnwantedDomains() {
        String email = "mail@google.com";

        boolean result = emailValidator.validate(email);

        assertFalse(result);
    }
}