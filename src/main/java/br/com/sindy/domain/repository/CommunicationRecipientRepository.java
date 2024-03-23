package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.email.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRecipientRepository extends JpaRepository<Recipient, String> {
}
