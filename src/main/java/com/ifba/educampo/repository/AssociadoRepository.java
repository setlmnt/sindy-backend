package com.ifba.educampo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long>{
	Optional<Page<Associado>> findByNome(String nome, Pageable pageable);
	Optional<Page<Associado>> findByCpf(String cpf, Pageable pageable);
	Optional<Page<Associado>> findByCarteiraSindical(Long carteiraSindical, Pageable pageable);
}
