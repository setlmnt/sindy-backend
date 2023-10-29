package com.ifba.educampo.service;

import com.ifba.educampo.dto.LocalOfficeDto;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.LocalOffice;
import com.ifba.educampo.repository.LocalOfficeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalOfficeService { // Delegacia (Escritorio Local)
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalOfficeService.class);
    private final GenericMapper<LocalOfficeDto, LocalOffice> modelMapper;
    private final LocalOfficeRepository localOfficeRepository;

    public Page<Associate> listAllAssociates(long localOfficeId, Pageable pageable) {
        LOGGER.info("Listing all associates from local office with ID: {}", localOfficeId);
        return localOfficeRepository.listAllAssociates(localOfficeId, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Associates from local office with ID {} not found.", localOfficeId);
                    return new NotFoundException("Associates Not Found");
                });
    }

    public LocalOffice findLocalOffice(long id) {
        LOGGER.info("Finding local office with ID: {}", id);
        return localOfficeRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Local office with ID {} not found.", id);
                    return new NotFoundException("Local Office Not Found");
                });
    }

    public Page<LocalOffice> listAll(Pageable pageable) {
        try {
            LOGGER.info("Listing all local offices.");
            return localOfficeRepository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing local offices.", e);
            throw new NotFoundException("An error occurred while listing local offices.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting local office with ID: {}", id);
            localOfficeRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing local offices.", e);
            throw new NotFoundException("An error occurred while deleting local office.");
        }
    }

    @Transactional
    public LocalOffice save(LocalOfficeDto localOfficeDto) {
        try {
            LOGGER.info("Saving local office.");
            return localOfficeRepository.save(modelMapper.mapDtoToModel(localOfficeDto, LocalOffice.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving local office.", e);
            throw new NotFoundException("An error occurred while saving local office.");
        }
    }

    @Transactional
    public LocalOffice replace(LocalOfficeDto localOfficeDto) {
        try {
            LOGGER.info("Replacing local office with ID: {}", localOfficeDto.getId());

            LocalOffice savedLocalOffice = findLocalOffice(localOfficeDto.getId());
            localOfficeDto.setId(savedLocalOffice.getId());

            return localOfficeRepository.save(modelMapper.mapDtoToModel(localOfficeDto, LocalOffice.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing local office.", e);
            throw new NotFoundException("An error occurred while replacing local office.");
        }
    }
}
