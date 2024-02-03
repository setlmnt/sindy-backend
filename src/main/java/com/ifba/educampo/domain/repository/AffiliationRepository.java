package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.associate.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
}
