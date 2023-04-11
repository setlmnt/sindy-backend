package com.ifba.educampo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long>{
	Optional<List<Associado>> findByNome(String nome);
	Optional<List<Associado>> findByCpf(String cpf);
	Optional<List<Associado>> findByCarteiraSindical(Long carteiraSindical);
}
