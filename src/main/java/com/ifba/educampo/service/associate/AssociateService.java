package com.ifba.educampo.service.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.associate.AssociatePostDto;
import com.ifba.educampo.dto.associate.AssociatePutDto;
import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.dto.localOffice.LocalOfficeResponseDto;
import com.ifba.educampo.exception.BadRequestListException;
import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.AddressMapper;
import com.ifba.educampo.mapper.LocalOfficeMapper;
import com.ifba.educampo.mapper.associate.AssociateMapper;
import com.ifba.educampo.mapper.associate.DependentsMapper;
import com.ifba.educampo.model.entity.Address;
import com.ifba.educampo.model.entity.associate.*;
import com.ifba.educampo.repository.associate.AssociateCustomRepository;
import com.ifba.educampo.repository.associate.AssociateRepository;
import com.ifba.educampo.service.AddressService;
import com.ifba.educampo.service.LocalOfficeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class AssociateService { // Classe de serviço para o Associado
    private final AssociateMapper associateMapper;
    private final AssociateRepository associateRepository;
    private final AssociateCustomRepository associateCustomRepository;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final DependentsService dependentsService;
    private final DependentsMapper dependentsMapper;
    private final WorkRecordService workRecordService;
    private final AffiliationService affiliationService;
    private final PlaceOfBirthService placeOfBirthService;
    private final LocalOfficeService localOfficeService;
    private final LocalOfficeMapper localOfficeMapper;

    public Page<AssociateResponseDto> findAll(String name, String cpf, Long unionCard, Pageable pageable) {
        log.info("Listing all associates");
        Page<Associate> associates = associateCustomRepository.findAllFromNameAndCpfAndUnionCard(
                name,
                cpf,
                unionCard,
                pageable
        );
        return associates.map(associateMapper::toResponseDto);
    }

    public AssociateResponseDto findById(Long id) {
        log.info("Finding associate with ID: {}", id);
        Associate associate = associateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Associate with ID {} not found", id);
                    return new NotFoundException("Associate Not Found");
                });
        return associateMapper.toResponseDto(associate);
    }

    public AssociateResponseDto save(AssociatePostDto dto) {
        log.info("Saving associate: {}", dto);

        validateAssociateCreation(dto);

        Associate associate = saveAssociate(dto);

        return associateMapper.toResponseDto(associate);
    }

    public AssociateResponseDto update(Long id, AssociatePutDto dto) {
        log.info("Replacing associate: {}", dto);

        validateAssociateUpdating(id, dto);

        Associate associate = associateRepository.getReferenceById(id);
        Associate updatedAssociate = getUpdatedAssociate(dto, associate);

        associate.update(updatedAssociate);
        return associateMapper.toResponseDto(associate);
    }

    public void delete(long id) {
        log.info("Deleting associate with ID: {}", id);
        Associate associate = associateRepository.getReferenceById(id);
        associate.delete();
    }

    public void deleteImage(Long associateId) {
        log.info("Deleting associate image with id {}", associateId);
        Associate associate = associateRepository.getReferenceById(associateId);
        associate.setPhoto(null);
    }

    private Associate saveAssociate(AssociatePostDto dto) {
        Associate associate = associateMapper.postDtoToEntity(dto);

        if (dto.address() != null) {
            Address address = addressService.save(dto.address());
            associate.setAddress(address);
        }

        if (dto.workRecord() != null) {
            WorkRecord workRecord = workRecordService.save(dto.workRecord());
            associate.setWorkRecord(workRecord);
        }

        if (dto.dependents() != null) {
            Dependents dependents = dependentsService.save(dto.dependents());
            associate.setDependents(dependents);
        }

        if (dto.affiliation() != null) {
            Affiliation affiliation = affiliationService.save(dto.affiliation());
            associate.setAffiliation(affiliation);
        }

        if (dto.placeOfBirth() != null) {
            PlaceOfBirth placeOfBirth = placeOfBirthService.save(dto.placeOfBirth());
            associate.setPlaceOfBirth(placeOfBirth);
        }

        associate = associateRepository.save(associate);

        if (dto.localOfficeId() != null) {
            LocalOfficeResponseDto localOfficeResponseDto = localOfficeService.findLocalOffice(dto.localOfficeId());
            addLocalOfficeToAssociates(localOfficeResponseDto.id(), associate.getId());
            associate.setLocalOffice(localOfficeMapper.responseDtoToEntityWithId(localOfficeResponseDto));
        }

        return associate;
    }

    private void validateAssociateUpdating(Long id, AssociatePutDto dto) {
        List<ErrorType> errorList = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem e se não são do próprio associado
        Associate associateCpf = associateRepository.findByCpf(dto.cpf());
        if (associateCpf != null && !Objects.equals(associateCpf.getId(), id)) {
            log.error("CPF already exists in another associate");
            errorList.add(new ErrorType("CPF already exists", "cpf"));
        }

        Associate associateUnionCard = associateRepository.findByUnionCard(dto.unionCard());
        if (associateUnionCard != null && !Objects.equals(associateUnionCard.getId(), id)) {
            log.error("Union Card already exists in another associate");
            errorList.add(new ErrorType("Union Card already exists", "unionCard"));
        }

        Associate associateRg = associateRepository.findByRg(dto.rg());
        if (associateRg != null && !Objects.equals(associateRg.getId(), id)) {
            log.error("RG already exists in another associate");
            errorList.add(new ErrorType("RG already exists", "rg"));
        }

        if (!errorList.isEmpty()) {
            throw new BadRequestListException("Invalid Associate", errorList);
        }
    }

    private void validateAssociateCreation(AssociatePostDto dto) {
        List<ErrorType> errorList = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem
        if (associateRepository.findByCpf(dto.cpf()) != null) {
            log.error("CPF already exists in another associate");
            errorList.add(new ErrorType("CPF already exists", "cpf"));
        }

        if (associateRepository.findByUnionCard(dto.unionCard()) != null) {
            log.error("Union Card already exists in another associate");
            errorList.add(new ErrorType("Union Card already exists", "unionCard"));
        }

        if (associateRepository.findByRg(dto.rg()) != null) {
            log.error("RG already exists in another associate");
            errorList.add(new ErrorType("RG already exists", "rg"));
        }

        if (!errorList.isEmpty()) {
            throw new BadRequestListException("Invalid Associate", errorList);
        }
    }

    private Associate getUpdatedAssociate(AssociatePutDto dto, Associate associate) {
        Associate updatedAssociate = associateMapper.putDtoToEntity(dto);

        if (dto.address() != null) {
            Address updatedAddress = null;

            if (associate.getAddress() != null) {
                updatedAddress = addressService.update(associate.getAddress().getId(), dto.address());
            } else {
                updatedAddress = addressService.save(addressMapper.toPostDto(dto.address()));
            }

            updatedAssociate.setAddress(updatedAddress);
        }

        if (dto.dependents() != null) {
            Dependents updatedDependents = null;

            if (associate.getDependents() != null) {
                updatedDependents = dependentsService.update(associate.getDependents().getId(), dto.dependents());
            } else {
                updatedDependents = dependentsService.save(dependentsMapper.toPostDto(dto.dependents()));
            }

            updatedAssociate.setDependents(updatedDependents);
        }

        if (dto.workRecord() != null) {
            WorkRecord updatedWorkRecord = workRecordService.update(associate.getWorkRecord().getId(), dto.workRecord());
            updatedAssociate.setWorkRecord(updatedWorkRecord);
        }

        if (dto.affiliation() != null) {
            Affiliation updatedAffiliation = affiliationService.update(associate.getAffiliation().getId(), dto.affiliation());
            updatedAssociate.setAffiliation(updatedAffiliation);
        }

        if (dto.placeOfBirth() != null) {
            PlaceOfBirth updatedPlaceOfBirth = placeOfBirthService.update(associate.getPlaceOfBirth().getId(), dto.placeOfBirth());
            updatedAssociate.setPlaceOfBirth(updatedPlaceOfBirth);
        }

        if (dto.localOfficeId() != null) {
            addLocalOfficeToAssociates(dto.localOfficeId(), associate.getId());
        }

        return updatedAssociate;
    }

    private void addLocalOfficeToAssociates(Long localOfficeId, Long associateId) {
        log.info("Adding local office to associates");
        associateRepository.addLocalOfficeToAssociates(localOfficeId, associateId);
    }
}
