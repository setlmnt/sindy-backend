package com.ifba.educampo.repository;

import com.ifba.educampo.entity.email.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRecipientRepository extends JpaRepository<Recipient, String> {
}
