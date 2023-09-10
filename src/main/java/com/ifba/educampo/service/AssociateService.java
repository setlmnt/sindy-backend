package com.ifba.educampo.service;

import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.AssociateException;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.*;
import com.ifba.educampo.model.entity.*;
import com.ifba.educampo.repository.AssociateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AssociateService { // Classe de serviço para o Associado
    private final GenericMapper<AssociateDto, Associate> modelMapper;
    private final AssociateRepository associateRepository;
    private final GenericMapper<AddressDto, Address> addressModelMapper;
    private final AddressService addressService;
    private final GenericMapper<WorkRecordDto, WorkRecord> workRecordModelMapper;
    private final WorkRecordService workRecordService;
    private final GenericMapper<DependentsDto, Dependents> dependentsModelMapper;
    private final DependentsService dependentsService;
    private final GenericMapper<AffiliationDto, Affiliation> affiliationModelMapper;
    private final AffiliationService affiliationService;
    private final GenericMapper<AssociatePhotoDto, AssociatePhoto> associatePhotoModelMapper;
    private final AssociatePhotoService associatePhotoService;
    private final GenericMapper<PlaceOfBirthDto, PlaceOfBirth> placeOfBirthModelMapper;
    private final PlaceOfBirthService placeOfBirthService;
    private final LocalOfficeService localOfficeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssociateService.class);

    public Associate findAssociate(Long id) {
        LOGGER.info("Finding associate with ID: {}", id);
        return associateRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Associate with ID {} not found", id);
                    return new NotFoundException("Associate Not Found");
                });
    }

    public Page<Associate> findAssociateByNameOrCpfOrUnionCard(String query, Pageable pageable) {
        LOGGER.info("Finding associate with query: {}", query);
        return associateRepository.findByNameOrCpfOrUnionCard(query, pageable)
                .orElseThrow(() -> {
                    LOGGER.error("Associate with query {} not found", query);
                    return new NotFoundException("Associate Not Found");
                });
    }

    public Page<Associate> listAll(Pageable pageable) {
        try {
            LOGGER.info("Listing all associates");
            return associateRepository.findAll(pageable);
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing associate: {}", e.getMessage());
            throw new RuntimeException("An error occurred while listing all associates.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting associate with ID: {}", id);
            associateRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing associate: {}", e.getMessage());
            throw new RuntimeException("An error occurred while deleting associate.");
        }
    }

    @Transactional
    public Associate save(AssociateDto associateDto) {
        try {
            LOGGER.info("Saving associate: {}", associateDto);

            associateDto.setId(null);

            List<ErrorType> errorList = new ArrayList<>();

            // Valida se o cpf, matrícula e rg já existem
            if (associateRepository.findByCpf(associateDto.getCpf()) != null) {
                LOGGER.error("CPF already exists in another associate");
                errorList.add(new ErrorType("CPF already exists", "cpf"));
            }

            if (associateRepository.findByUnionCard(associateDto.getUnionCard()) != null) {
                LOGGER.error("Union Card already exists in another associate");
                errorList.add(new ErrorType("Union Card already exists", "unionCard"));
            }

            if (associateRepository.findByRg(associateDto.getRg()) != null) {
                LOGGER.error("RG already exists in another associate");
                errorList.add(new ErrorType("RG already exists", "rg"));
            }

            if (!errorList.isEmpty()) {
                throw new AssociateException("Invalid Associate", errorList);
            }

            Associate associate = modelMapper.mapDtoToModel(associateDto, Associate.class);
            associate.getAddress().setId(null);
            associate.getWorkRecord().setId(null);
            associate.getDependents().setId(null);
            associate.getAffiliation().setId(null);
            associate.getAssociatePhoto().setId(null);
            associate.getPlaceOfBirth().setId(null);
            if (associateDto.getLocalOfficeId() != null) {
                associate.setLocalOffice(localOfficeService.findLocalOffice(associateDto.getLocalOfficeId()));
            }

            return associateRepository.save(associate);
        } catch (AssociateException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing associate: {}", e.getMessage());
            throw new RuntimeException("An error occurred while saving associate.");
        }
    }

    @Transactional
    public void replace(AssociateDto associateDto) {
        try {
            LOGGER.info("Replacing associate: {}", associateDto);

            Associate savedAssociate = findAssociate(associateDto.getId());

            List<ErrorType> errorList = new ArrayList<>();

            // Valida se o cpf, matrícula e rg já existem e se não são do próprio associado
            Associate associateCpf = associateRepository.findByCpf(associateDto.getCpf());
            if (associateCpf != null && !Objects.equals(associateCpf.getId(), associateDto.getId())) {
                LOGGER.error("CPF already exists in another associate");
                errorList.add(new ErrorType("CPF already exists", "cpf"));
            }

            Associate associateUnionCard = associateRepository.findByUnionCard(associateDto.getUnionCard());
            if (associateUnionCard != null && !Objects.equals(associateUnionCard.getId(), associateDto.getId())) {
                LOGGER.error("Union Card already exists in another associate");
                errorList.add(new ErrorType("Union Card already exists", "unionCard"));
            }

            Associate associateRg = associateRepository.findByRg(associateDto.getRg());
            if (associateRg != null && !Objects.equals(associateRg.getId(), associateDto.getId())) {
                LOGGER.error("RG already exists in another associate");
                errorList.add(new ErrorType("RG already exists", "rg"));
            }

            if (!errorList.isEmpty()) {
                throw new AssociateException("Invalid Associate", errorList);
            }

            // Mapeie os objetos DTO para entidades atualizadas
            Address updatedAddress = addressService.replace(associateDto.getAddress(), savedAssociate.getAddress().getId());
            WorkRecord updatedWorkRecord = workRecordService.replace(associateDto.getWorkRecord(), savedAssociate.getWorkRecord().getId());
            Dependents updatedDependents = dependentsService.replace(associateDto.getDependents(), savedAssociate.getDependents().getId());
            Affiliation updatedAffiliation = affiliationService.replace(associateDto.getAffiliation(), savedAssociate.getAffiliation().getId());
            AssociatePhoto updatedAssociatePhoto = associatePhotoService.replace(associateDto.getAssociatePhoto(), savedAssociate.getAssociatePhoto().getId());
            PlaceOfBirth updatedPlaceOfBirth = placeOfBirthService.replace(associateDto.getPlaceOfBirth(), savedAssociate.getPlaceOfBirth().getId());

            // Atualize os valores do associado
            associateDto.setId(savedAssociate.getId());
            associateDto.setAddress(addressModelMapper.mapModelToDto(updatedAddress, AddressDto.class));
            associateDto.setWorkRecord(workRecordModelMapper.mapModelToDto(updatedWorkRecord, WorkRecordDto.class));
            associateDto.setDependents(dependentsModelMapper.mapModelToDto(updatedDependents, DependentsDto.class));
            associateDto.setAffiliation(affiliationModelMapper.mapModelToDto(updatedAffiliation, AffiliationDto.class));
            associateDto.setAssociatePhoto(associatePhotoModelMapper.mapModelToDto(updatedAssociatePhoto, AssociatePhotoDto.class));
            associateDto.setPlaceOfBirth(placeOfBirthModelMapper.mapModelToDto(updatedPlaceOfBirth, PlaceOfBirthDto.class));

            // Atualize o ID do local office se for fornecido
            LocalOffice localOffice = null;
            if (associateDto.getLocalOfficeId() != null) {
                localOffice = localOfficeService.findLocalOffice(associateDto.getLocalOfficeId());
            }

            Associate updatedAssociate = modelMapper.mapDtoToModel(associateDto, Associate.class);
            updatedAssociate.setLocalOffice(localOffice);

            associateRepository.save(updatedAssociate);
        } catch (AssociateException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing associate: {}", e.getMessage());
            throw new RuntimeException("An error occurred while replacing associate.");
        }
    }
}
