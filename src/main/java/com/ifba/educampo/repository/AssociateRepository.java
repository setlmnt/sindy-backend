package com.ifba.educampo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long>{ // Interface de repositório para o Associado
	Optional<Page<Associate>> findByName(String name, Pageable pageable);
	Optional<Page<Associate>> findByCpf(Long cpf, Pageable pageable);
	Optional<Page<Associate>> findByUnionCard(Long unionCard, Pageable pageable);
}
