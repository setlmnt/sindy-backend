package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.AssociatePhoto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AssociatePhotoRepository extends JpaRepository<AssociatePhoto, Long> { // Interface de repositório para a foto do associado
    @Query(
            "SELECT ap FROM AssociatePhoto ap WHERE ap.id = (SELECT a.associatePhoto.id FROM Associate a WHERE a.id = :associateId)"
    ) // Query para buscar a foto do Associado pelo id do Associado
    AssociatePhoto findAssociatePhotoByAssociateId(Long associateId); // Query para buscar a foto do Associado pelo id do Associado

    @Modifying
    @Transactional
    @Query(
            "DELETE FROM AssociatePhoto ap WHERE ap.id = (SELECT a.associatePhoto.id FROM Associate a WHERE a.id = :associateId)"
    )
    void deleteAssociatePhotoByAssociateId(Long associateId); // Query para deletar a foto do Associado pelo id do Associado
}
