package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AssociatePhotoDto;
import com.ifba.educampo.model.entity.AssociatePhoto;
import com.ifba.educampo.repository.AssociatePhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssociatePhotoService { // Foto do associado
    private final static Logger LOGGER = LoggerFactory.getLogger(AssociatePhotoService.class);
    private final GenericMapper<AssociatePhotoDto, AssociatePhoto> modelMapper;
    private final AssociatePhotoRepository associatePhotoRepository;

    public AssociatePhoto findAssociatePhoto(Long id) {
        LOGGER.info("Finding associate photo with ID: {}", id);
        return associatePhotoRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Associate photo with ID {} not found.", id);
                    return new NotFoundException("Associate photo Not Found");
                });
    }

    public List<AssociatePhoto> listAll() {
        try {
            LOGGER.info("Listing all associate photos.");
            return associatePhotoRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while listing associate photos.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting associate photo with ID: {}", id);
            associatePhotoRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while deleting associate photo.", e);
        }
    }

    @Transactional
    public AssociatePhoto save(AssociatePhotoDto associatePhotoDto) {
        try {
            LOGGER.info("Saving associate photo.");
            return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while saving associate photo.", e);
        }
    }

    @Transactional
    public AssociatePhoto replace(AssociatePhotoDto associatePhotoDto, Long assocaitePhotoId) {
        try {
            LOGGER.info("Replacing associate photo.");
            AssociatePhoto savedAssociatePhoto = findAssociatePhoto(assocaitePhotoId);
            associatePhotoDto.setId(savedAssociatePhoto.getId());

            return associatePhotoRepository.save(modelMapper.mapDtoToModel(associatePhotoDto, AssociatePhoto.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing associate photos.", e);
            throw new RuntimeException("An error occurred while replacing associate photo.", e);
        }
    }
}
