package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AffiliationDto;
import com.ifba.educampo.model.entity.Affiliation;
import com.ifba.educampo.repository.AffiliationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AffiliationService { // Afiliacao
    private static final Logger LOGGER = LoggerFactory.getLogger(AffiliationService.class);
    private final GenericMapper<AffiliationDto, Affiliation> modelMapper;
    private final AffiliationRepository affiliationRepository;

    public Affiliation findAffiliation(Long id) {
        LOGGER.info("Finding affiliation with ID: {}", id);
        return affiliationRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Affiliation with ID {} not found.", id);
                    return new NotFoundException("Affiliation Not Found");
                });
    }

    public List<Affiliation> listAll() {
        try {
            LOGGER.info("Listing all affiliations.");
            return affiliationRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing affiliations.", e);
            throw new RuntimeException("An error occurred while listing affiliations.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting affiliation with ID: {}", id);
            affiliationRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing affiliations.", e);
            throw new RuntimeException("An error occurred while deleting affiliation.", e);
        }
    }

    @Transactional
    public Affiliation save(AffiliationDto affiliationDto) {
        try {
            LOGGER.info("Saving affiliation.");
            return affiliationRepository.save(modelMapper.mapDtoToModel(affiliationDto, Affiliation.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing affiliations.", e);
            throw new RuntimeException("An error occurred while saving affiliation.", e);
        }
    }

    @Transactional
    public Affiliation replace(AffiliationDto affiliationDto, Long affiliationId) {
        try {
            LOGGER.info("Replacing affiliation with ID: {}", affiliationId);

            Affiliation affiliation = findAffiliation(affiliationId);
            affiliationDto.setId(affiliation.getId());

            return affiliationRepository.save(modelMapper.mapDtoToModel(affiliationDto, Affiliation.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing affiliations.", e);
            throw new RuntimeException("An error occurred while replacing affiliation.", e);
        }
    }
}
