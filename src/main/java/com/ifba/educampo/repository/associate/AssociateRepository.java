package com.ifba.educampo.repository.associate;

import com.ifba.educampo.entity.associate.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssociateRepository extends JpaRepository<Associate, Long> { // Interface de repositório para o Associado
    Page<Associate> findAll(Pageable pageable); // Query para listar todos os Associados

    Associate findByCpf(String cpf); // Query para buscar o Associado pelo cpf

    Associate findByUnionCard(Long unionCard); // Query para buscar o Associado pela matrícula

    Associate findByRg(String rg); // Query para buscar o Associado pelo rg

    @Modifying
    @Query(
            "UPDATE Associate a SET a.profilePicture = (SELECT i FROM File i WHERE i.id = :imageId) WHERE a.id = :id"
    )
        // Query para salvar a foto do Associado pelo id do Associado
    void savePhoto(@Param("id") Long id, @Param("imageId") Long imageId);

    @Modifying
    @Query(
            "UPDATE Associate a SET a.localOffice = (SELECT l FROM LocalOffice l WHERE l.id = :localOfficeId) WHERE a.id = :associateId"
    )
    void addLocalOfficeToAssociates(@Param("localOfficeId") Long localOfficeId, @Param("associateId") Long associateId);
}
