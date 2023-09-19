package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.AssociatePhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssociateRepository extends JpaRepository<Associate, Long> { // Interface de repositório para o Associado
    Page<Associate> findAll(Pageable pageable); // Query para listar todos os Associados

    @Query(
            "SELECT a FROM Associate a WHERE a.name LIKE %?1% OR a.cpf LIKE %?1% OR CAST(a.unionCard AS string) LIKE %?1%"
    )
        // Query para buscar o Associado pelo nome, cpf ou matrícula
    Optional<Page<Associate>> findByNameOrCpfOrUnionCard(String query, Pageable pageable);

    Associate findByCpf(String cpf); // Query para buscar o Associado pelo cpf

    Associate findByUnionCard(Long unionCard); // Query para buscar o Associado pela matrícula

    Associate findByRg(String rg); // Query para buscar o Associado pelo rg
}
