package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
}
