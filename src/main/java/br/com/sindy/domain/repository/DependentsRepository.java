package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.Dependents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentsRepository extends JpaRepository<Dependents, Long> {
}
