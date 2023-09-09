package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> { // Interface de repositório para a afiliação

}
