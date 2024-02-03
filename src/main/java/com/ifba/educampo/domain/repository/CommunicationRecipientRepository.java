package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.email.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRecipientRepository extends JpaRepository<Recipient, String> {
}
