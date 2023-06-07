package com.ifba.educampo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Affiliation;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long>{ // Interface de repositório para a afiliação

}
