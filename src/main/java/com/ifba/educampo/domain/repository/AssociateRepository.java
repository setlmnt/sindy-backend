package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.associate.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociateRepository extends JpaRepository<Associate, Long>, CustomAssociateRepository {
    Page<Associate> findAll(Pageable pageable); // Query para listar todos os Associados

    Associate findByCpf(String cpf); // Query para buscar o Associado pelo cpf

    Associate findByUnionCard(Long unionCard); // Query para buscar o Associado pela matrícula

    Associate findByRg(String rg); // Query para buscar o Associado pelo rg

    @Modifying
    @Query(
            "UPDATE Associate a SET a.profilePicture = (SELECT i FROM File i WHERE i.id = :imageId) WHERE a.id = :id"
    )
    void savePhoto(@Param("id") Long id, @Param("imageId") Long imageId);

    @Modifying
    @Query(
            "UPDATE Associate a SET a.localOffice = (SELECT l FROM LocalOffice l WHERE l.id = :localOfficeId) WHERE a.id = :associateId"
    )
    void addLocalOfficeToAssociates(@Param("localOfficeId") Long localOfficeId, @Param("associateId") Long associateId);

    @Query(
            "SELECT DISTINCT a.id FROM Associate a LEFT JOIN MonthlyFee mf ON a.id = mf.associate.id WHERE a.deleted = false AND a.isPaid = true AND mf.deleted = false AND CURRENT_DATE > (" +
                    "SELECT mf2.finalDate FROM MonthlyFee mf2 JOIN Associate a2 ON mf2.associate.id = a2.id WHERE a2.id = a.id and mf2.deleted = false AND a2.deleted = false ORDER BY mf2.finalDate DESC LIMIT 1" +
                    ")"
    )
    List<Long> findAllAssociatesWithExpiredMonthlyFee(); // Query para listar todos os Associados com a mensalidade vencida

    @Modifying
    @Query(
            "UPDATE Associate a SET a.isPaid = :isPaid WHERE a.id IN :ids"
    )
    void updateAssociatesIsPaidByIds(List<Long> ids, boolean isPaid);
}
