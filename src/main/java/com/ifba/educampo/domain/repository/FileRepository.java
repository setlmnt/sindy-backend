package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query(
            "SELECT i FROM File i WHERE i.id = (SELECT a.profilePicture.id FROM Associate a WHERE a.id = :associateId AND a.deleted = false)"
    )
    Optional<File> findProfilePictureByAssociateId(Long associateId); // Query para buscar a foto do Associado pelo id do Associado

    @Query(
            "SELECT i FROM File i WHERE i.associate.id = :associateId AND i.deleted = false"
    )
    Page<File> findImagesByAssociateId(Long associateId, Pageable pageable);

    Optional<File> findByIdAndDeletedFalse(Long documentId);
}
