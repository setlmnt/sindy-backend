package com.ifba.educampo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Dependents;

public interface DependentsRepository extends JpaRepository<Dependents, Long>{ // Interface de repositório para os dependentes

}
