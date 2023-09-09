package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AssociatePhotoDto;
import com.ifba.educampo.model.entity.AssociatePhoto;
import com.ifba.educampo.repository.AssociatePhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociatePhotoService { // Foto do associado
    private final GenericMapper<AssociatePhotoDto, AssociatePhoto> modelMapper;
    private final AssociatePhotoRepository associatePhotoRepository;

    public AssociatePhoto findAssociatePhoto(Long id) {
        return associatePhotoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Associate photo Not Found"));
    }

    public List<AssociatePhoto> listAll() {
        return associatePhotoRepository.findAll();
    }

    public void delete(long id) {
        associatePhotoRepository.delete(findAssociatePhoto(id));
    }

    @Transactional
    public AssociatePhoto save(AssociatePhotoDto associatePhotoDto) {
        return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));
    }

    public AssociatePhoto replace(AssociatePhotoDto associatePhotoDto, Long assocaitePhotoId) {
        AssociatePhoto savedAssociatePhoto = findAssociatePhoto(assocaitePhotoId);
        associatePhotoDto.setId(savedAssociatePhoto.getId());

        return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));

    }
}
