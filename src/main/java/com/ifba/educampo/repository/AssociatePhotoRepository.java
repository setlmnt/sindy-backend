package com.ifba.educampo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.AssociatePhoto;

public interface AssociatePhotoRepository extends JpaRepository<AssociatePhoto, Long>{ // Interface de repositório para a foto do associado

}
