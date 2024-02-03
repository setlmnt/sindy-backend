package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.associate.Dependents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentsRepository extends JpaRepository<Dependents, Long> {
}
