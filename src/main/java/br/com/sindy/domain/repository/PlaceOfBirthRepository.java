package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.PlaceOfBirth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceOfBirthRepository extends JpaRepository<PlaceOfBirth, Long> {
}
