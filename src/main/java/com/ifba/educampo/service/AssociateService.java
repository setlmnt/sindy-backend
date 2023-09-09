package com.ifba.educampo.service;

import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.InvalidAssociateException;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.*;
import com.ifba.educampo.model.entity.*;
import com.ifba.educampo.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Associate findAssociate(Long id) {
        return associateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Associate Not Found"));
    }

    public Page<Associate> findAssociateByNameOrCpfOrUnionCard(String query, Pageable pageable) {
        return associateRepository.findByNameOrCpfOrUnionCard(query, pageable)
                .orElseThrow(() -> new NotFoundException("Associate Not Found"));
    }

    public Page<Associate> listAll(Pageable pageable) {
        return associateRepository.findAll(pageable);
    }

    @Transactional
    public void delete(long id) {
        associateRepository.delete(findAssociate(id));
    }

    @Transactional
    public Associate save(AssociateDto associateDto) {
        associateDto.setId(null);

        List<ErrorType> errorList = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem
        if (associateRepository.findByCpf(associateDto.getCpf()) != null) {
            errorList.add(new ErrorType("CPF already exists", "cpf"));
        }

        if (associateRepository.findByUnionCard(associateDto.getUnionCard()) != null) {
            errorList.add(new ErrorType("Union Card already exists", "unionCard"));
        }

        if (associateRepository.findByRg(associateDto.getRg()) != null) {
            errorList.add(new ErrorType("RG already exists", "rg"));
        }

        if (!errorList.isEmpty()) {
            throw new InvalidAssociateException("Invalid Associate", errorList);
        }

        Associate associate = modelMapper.mapDtoToModel(associateDto, Associate.class);
        associate.getAddress().setId(null);
        associate.getWorkRecord().setId(null);
        associate.getDependents().setId(null);
        associate.getAffiliation().setId(null);
        associate.getAssociatePhoto().setId(null);
        associate.getPlaceOfBirth().setId(null);

        return associateRepository.save(associate);
    }

    @Transactional
    public void replace(AssociateDto associateDto) {
        Associate savedAssociate = findAssociate(associateDto.getId());

        List<ErrorType> errorList = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem e se não são do próprio associado
        Associate associateCpf = associateRepository.findByCpf(associateDto.getCpf());
        if (associateCpf != null && !Objects.equals(associateCpf.getId(), associateDto.getId())) {
            errorList.add(new ErrorType("CPF already exists", "cpf"));
        }

        Associate associateUnionCard = associateRepository.findByUnionCard(associateDto.getUnionCard());
        if (associateUnionCard != null && !Objects.equals(associateUnionCard.getId(), associateDto.getId())) {
            errorList.add(new ErrorType("Union Card already exists", "unionCard"));
        }

        Associate associateRg = associateRepository.findByRg(associateDto.getRg());
        if (associateRg != null && !Objects.equals(associateRg.getId(), associateDto.getId())) {
            errorList.add(new ErrorType("RG already exists", "rg"));
        }

        if (!errorList.isEmpty()) {
            throw new InvalidAssociateException("Invalid Associate", errorList);
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
    }
}
