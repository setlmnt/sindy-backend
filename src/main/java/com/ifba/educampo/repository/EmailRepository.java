package com.ifba.educampo.repository;

import com.ifba.educampo.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long>, CustomEmailRepository {
}
