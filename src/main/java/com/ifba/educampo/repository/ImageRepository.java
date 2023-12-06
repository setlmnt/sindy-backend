package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> { // Interface de repositório para a foto do associado
    @Query(
            "SELECT i FROM Image i WHERE i.id = (SELECT a.profilePicture.id FROM Associate a WHERE a.id = :associateId AND a.deleted = false)"
    )
        // Query para buscar a foto do Associado pelo id do Associado
    Optional<Image> findProfilePictureByAssociateId(Long associateId); // Query para buscar a foto do Associado pelo id do Associado

    @Modifying
    @Query(
            "DELETE FROM Image i WHERE i.id = (SELECT a.profilePicture.id FROM Associate a WHERE a.id = :associateId)"
    )
    void deleteProfilePictureByAssociateId(Long associateId); // Query para deletar a foto do Associado pelo id do Associado
}
