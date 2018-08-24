package com.frontech.springofemails.recorder;

import com.frontech.springofemails.recorder.wrapper.CassandraBatchWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraTemplate;

@SpringBootApplication
public class Application {

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "cassandraBatchWrapper")
    public CassandraBatchWrapper cassandraBatchWrapper() {
        return new CassandraBatchWrapper(cassandraTemplate);
    }

}
