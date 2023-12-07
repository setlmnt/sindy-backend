package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> { // Interface de repositório para a foto do associado
    @Query(
            "SELECT i FROM File i WHERE i.id = (SELECT a.profilePicture.id FROM Associate a WHERE a.id = :associateId AND a.deleted = false)"
    )
        // Query para buscar a foto do Associado pelo id do Associado
    Optional<File> findProfilePictureByAssociateId(Long associateId); // Query para buscar a foto do Associado pelo id do Associado

    @Modifying
    @Query(
            "DELETE FROM File i WHERE i.id = (SELECT a.profilePicture.id FROM Associate a WHERE a.id = :associateId)"
    )
    void deleteProfilePictureByAssociateId(Long associateId); // Query para deletar a foto do Associado pelo id do Associado

    @Query(
            "SELECT i FROM File i WHERE i.associate.id = :associateId AND i.deleted = false"
    )
    Page<File> findImagesByAssociateId(Long associateId, Pageable pageable);

    Optional<File> findByIdAndDeletedFalse(Long documentId);
}
