package com.frontech.springofemails.collector.dto;

public class EmailDTO {

    private String email;
    private Long count;

    public EmailDTO(String email, Long count) {
        this.email = email;
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
