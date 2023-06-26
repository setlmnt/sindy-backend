package com.ifba.educampo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Associate;
import org.springframework.data.jpa.repository.Query;

public interface AssociateRepository extends JpaRepository<Associate, Long>{ // Interface de repositório para o Associado
	@Query("SELECT a FROM Associate a WHERE a.name LIKE %?1% OR a.cpf LIKE %?1% OR CAST(a.unionCard AS string) LIKE %?1%") // Query para buscar o Associado pelo nome, cpf ou matrícula
	Optional<Page<Associate>> findByNameOrCpfOrUnionCard(String query, Pageable pageable);
}
