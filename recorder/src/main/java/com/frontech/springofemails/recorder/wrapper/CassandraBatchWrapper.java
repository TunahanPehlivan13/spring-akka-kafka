package com.frontech.springofemails.recorder.wrapper;

import com.frontech.springofemails.data.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.WriteResult;

import java.util.ArrayList;
import java.util.List;

public class CassandraBatchWrapper {

    private static final Logger logger = LoggerFactory.getLogger(CassandraBatchWrapper.class);

    private CassandraTemplate cassandraTemplate;
    private CassandraBatchOperations cassandraBatchOperations;
    private boolean waitExecution = false;
    private List<Email> insertAfterExecution = new ArrayList<>();

    public CassandraBatchWrapper(CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
        cassandraBatchOperations = cassandraTemplate.batchOps();
    }

    public void insert(Email email) {
        if (waitExecution) {
            insertAfterExecution.add(email);
        } else {
            cassandraBatchOperations.insert(email);
        }
    }

    public void execute() {
        waitExecution = true;
        WriteResult result = cassandraBatchOperations.execute();
        if (result.wasApplied()) {
            logger.info("Bulk data is executed");
            cassandraBatchOperations = cassandraTemplate.batchOps();

            waitExecution = false;
            cassandraBatchOperations.insert(insertAfterExecution);
            insertAfterExecution.clear();
        } else {
            logger.error("There was something wrong!");
        }
    }
}
