package com.ifba.educampo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long>{
	Optional<Associado> findByNome(String nome);
	Optional<Associado> findByCpf(String cpf);
	Optional<Associado> findByCarteiraSindical(Long carteiraSindical);
}
