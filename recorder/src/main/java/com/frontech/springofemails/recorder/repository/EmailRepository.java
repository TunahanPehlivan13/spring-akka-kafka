package com.frontech.springofemails.recorder.repository;

import com.frontech.springofemails.recorder.data.Email;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<Email, String> {
}
