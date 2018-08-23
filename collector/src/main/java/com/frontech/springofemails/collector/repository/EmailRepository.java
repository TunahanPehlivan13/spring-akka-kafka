package com.frontech.springofemails.collector.repository;

import com.frontech.springofemails.data.Email;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EmailRepository extends CrudRepository<Email, UUID> {

    List<Email> findByText(final String text);

    List<Email> findAll();
}
