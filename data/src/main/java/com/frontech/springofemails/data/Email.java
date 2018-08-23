package com.frontech.springofemails.data;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class Email {

    @PrimaryKey
    private String email;

    private Integer count;

    public Email() {
        super();
    }

    public Email(String email, Integer count) {
        this.email = email;
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
