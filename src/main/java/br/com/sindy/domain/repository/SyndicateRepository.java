package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.Syndicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SyndicateRepository extends JpaRepository<Syndicate, Long> {
    @Query(
            value = "SELECT * FROM syndicate LIMIT 1",
            nativeQuery = true
    )
    Optional<Syndicate> find();
}
