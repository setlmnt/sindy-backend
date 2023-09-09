package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.Dependents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentsRepository extends JpaRepository<Dependents, Long> { // Interface de repositório para os dependentes

}
