package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.PlaceOfBirthDto;
import com.ifba.educampo.model.entity.PlaceOfBirth;
import com.ifba.educampo.repository.PlaceOfBirthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceOfBirthService { // Naturalidade
    private final GenericMapper<PlaceOfBirthDto, PlaceOfBirth> modelMapper;
    private final PlaceOfBirthRepository placeOfBirthRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaceOfBirthService.class);

    public PlaceOfBirth findPlaceOfBirth(Long id) {
        LOGGER.info("Finding place of birth with ID: {}", id);
        return placeOfBirthRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Place of birth with ID {} not found.", id);
                    return new NotFoundException("Place Of Birth Not Found");
                });
    }

    public List<PlaceOfBirth> listAll() {
        try {
            LOGGER.info("Listing all places of birth.");
            return placeOfBirthRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing places of birth.", e);
            throw new NotFoundException("An error occurred while listing places of birth.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting place of birth with ID: {}", id);
            placeOfBirthRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing places of birth.", e);
            throw new NotFoundException("An error occurred while deleting place of birth.");
        }
    }

    @Transactional
    public PlaceOfBirth save(PlaceOfBirthDto placeOfBirthDto) {
        try {
            LOGGER.info("Saving place of birth.");
            return placeOfBirthRepository.save(modelMapper.mapDtoToModel(placeOfBirthDto, PlaceOfBirth.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving place of birth.", e);
            throw new NotFoundException("An error occurred while saving place of birth.");
        }
    }

    @Transactional
    public PlaceOfBirth replace(PlaceOfBirthDto placeOfBirthDto, Long placeOfBirthId) {
        try {
            LOGGER.info("Replacing place of birth with ID: {}", placeOfBirthId);
            PlaceOfBirth savedPlaceOfBirth = findPlaceOfBirth(placeOfBirthId);
            placeOfBirthDto.setId(savedPlaceOfBirth.getId());

            return placeOfBirthRepository.save(modelMapper.mapDtoToModel(placeOfBirthDto, PlaceOfBirth.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing place of birth.", e);
            throw new NotFoundException("An error occurred while replacing place of birth.");
        }
    }
}
