package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.DependentsDto;
import com.ifba.educampo.model.entity.Dependents;
import com.ifba.educampo.repository.DependentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DependentsService { // Dependentes
    private final GenericMapper<DependentsDto, Dependents> modelMapper;
    private final DependentsRepository dependentsRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(DependentsService.class);

    public Dependents findDependent(Long id) {
        LOGGER.info("Finding dependent with ID: {}", id);
        return dependentsRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Dependent with ID {} not found.", id);
                    return new NotFoundException("Dependent Not Found");
                });
    }

    public List<Dependents> listAll() {
        try {
            LOGGER.info("Listing all dependents.");
            return dependentsRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing dependents.", e);
            throw new NotFoundException("An error occurred while listing dependents.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting dependent with ID: {}", id);
            dependentsRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing dependents.", e);
            throw new NotFoundException("An error occurred while deleting dependent.");
        }
    }

    @Transactional
    public Dependents save(DependentsDto dependentsDto) {
        try {
            LOGGER.info("Saving dependent.");
            return dependentsRepository.save(modelMapper.mapDtoToModel(dependentsDto, Dependents.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving dependent.", e);
            throw new NotFoundException("An error occurred while saving dependent.");
        }
    }

    @Transactional
    public Dependents replace(DependentsDto dependentsDto, Long dependentId) {
        try {
            LOGGER.info("Replacing dependent with ID: {}", dependentId);

            Dependents savedDependents = findDependent(dependentId);
            dependentsDto.setId(savedDependents.getId());

            return dependentsRepository.save(modelMapper.mapDtoToModel(dependentsDto, Dependents.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing dependent.", e);
            throw new NotFoundException("An error occurred while replacing dependent.");
        }
    }
}
