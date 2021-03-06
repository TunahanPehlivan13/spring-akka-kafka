package com.frontech.springofemails.collector.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9_.+-]+@(?:(?:[A-Z0-9-]+\\.)?[A-Z]+\\.)?(comeon.com|cherry.se)$", Pattern.CASE_INSENSITIVE);

    public boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
