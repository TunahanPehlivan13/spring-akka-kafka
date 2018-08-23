package com.frontech.springofemails.collector.com.frontech.springofemails.repository;

import com.frontech.springofemails.data.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, String> {
}
