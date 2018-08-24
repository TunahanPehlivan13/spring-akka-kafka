package com.frontech.springofemails.recorder.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.core.CassandraBatchOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.WriteResult;

public class CassandraBatchWrapper {

    private static final Logger logger = LoggerFactory.getLogger(CassandraBatchWrapper.class);

    private CassandraTemplate cassandraTemplate;
    private CassandraBatchOperations cassandraBatchOperations;

    public CassandraBatchWrapper(CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
        cassandraBatchOperations = cassandraTemplate.batchOps();
    }

    public void insert(Object... objects) {
        cassandraBatchOperations.insert(objects);
    }

    public void execute() {
        WriteResult result = cassandraBatchOperations.execute();
        if (result.wasApplied()) {
            logger.info("Bulk data is executed");
        } else {
            logger.error("There was something wrong!");
        }
        cassandraBatchOperations = cassandraTemplate.batchOps();
    }
}
