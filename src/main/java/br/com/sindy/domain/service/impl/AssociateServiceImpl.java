package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.AssociatePostDto;
import br.com.sindy.domain.dto.associate.AssociatePutDto;
import br.com.sindy.domain.dto.associate.AssociateResponseDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficeResponseDto;
import br.com.sindy.domain.entity.Address;
import br.com.sindy.domain.entity.Template;
import br.com.sindy.domain.entity.associate.*;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.enums.PeriodEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.exception.ExceptionResponse;
import br.com.sindy.domain.mapper.AddressMapper;
import br.com.sindy.domain.mapper.LocalOfficeMapper;
import br.com.sindy.domain.mapper.associate.AssociateMapper;
import br.com.sindy.domain.mapper.associate.DependentsMapper;
import br.com.sindy.domain.repository.AssociateRepository;
import br.com.sindy.domain.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class AssociateServiceImpl implements AssociateService {
    public static final String SYNDICATE_REPORT = "syndicate.report";
    public static final String ASSOCIATE_REPORT = "associate.report";
    public static final String MEMBERSHIP_CARD_REPORT = "membership.card.report";

    private final AssociateMapper associateMapper;
    private final AssociateRepository associateRepository;
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    private final DependentsService dependentsService;
    private final DependentsMapper dependentsMapper;
    private final WorkRecordService workRecordService;
    private final AffiliationService affiliationService;
    private final PlaceOfBirthService placeOfBirthService;
    private final LocalOfficeService localOfficeService;
    private final LocalOfficeMapper localOfficeMapper;
    private final ReportService reportService;
    private final TemplateService templateService;

    public Page<AssociateResponseDto> findAll(String query, Pageable pageable) {
        log.info("Listing all associates");
        Page<Associate> associates = associateRepository.findAllFromNameAndCpfAndUnionCard(
                query,
                pageable
        );
        return associates.map(associateMapper::toResponseDto);
    }

    public AssociateResponseDto findById(Long id) {
        log.info("Finding associate with ID: {}", id);
        Associate associate = associateRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Associate with ID {} not found", id);
                    return new ApiException(ErrorEnum.ASSOCIATE_NOT_FOUND);
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

    public void delete(Long id) {
        log.info("Deleting associate with ID: {}", id);
        Associate associate = associateRepository.getReferenceById(id);
        associate.delete();
    }

    public void deleteImage(Long associateId) {
        log.info("Deleting associate image with id {}", associateId);
        Associate associate = associateRepository.getReferenceById(associateId);
        associate.setProfilePicture(null);
    }

    public Page<AssociateResponseDto> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period) {
        log.info("Listing all birthday associates");
        Page<Associate> associates = associateRepository.findAllBirthdayAssociates(pageable, period);
        return associates.map(associateMapper::toResponseDto);
    }

    public void updatePaidStatus(Long id, boolean status) {
        log.info("Updating paid status of associate with ID: {}", id);
        Associate associate = associateRepository.getReferenceById(id);
        associate.setIsPaid(status);
    }

    public byte[] exportAssociateToPdf(Long id, HttpServletResponse response) {
        AssociateResponseDto associateResponseDto = findById(id);
        Associate associate = associateMapper.responseDtoToEntity(associateResponseDto);

        Template syndicateTemplate = templateService.getTemplate(SYNDICATE_REPORT);
        JasperReport syndicateReport = reportService.compileReport(syndicateTemplate);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("associateId", id);
        parameters.put("syndicateReport", syndicateReport);

        Template assocaiteTemplate = templateService.getTemplate(ASSOCIATE_REPORT);
        byte[] report = reportService.generatePdfReport(assocaiteTemplate, parameters);

        String fileName = associate.getName().replace(" ", "_") + "-" + associate.getUnionCard() + ".pdf";
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName
        );

        return report;
    }

    public byte[] exportAssociateMembershipCardToPdf(Long id, HttpServletResponse response) {
        AssociateResponseDto associateResponseDto = findById(id);
        Associate associate = associateMapper.responseDtoToEntity(associateResponseDto);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("associateId", id);

        Template membershipTemplate = templateService.getTemplate(MEMBERSHIP_CARD_REPORT);
        byte[] report = reportService.generatePdfReport(membershipTemplate, parameters);

        String fileName = associate.getName() + "-" + associate.getUnionCard() + ".pdf";
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName.replace(" ", "_")
        );

        return report;
    }

    @Scheduled(cron = "1 0 0 * * *") // Every day at 12:00:01 AM
    public void setAssociateIsPaidToFalseWhenMonthlyFeeAsAlreadyExpired() {
        List<Long> associatesWithExpiredMonthlyFee = associateRepository.findAllAssociatesWithExpiredMonthlyFee();
        if (associatesWithExpiredMonthlyFee.isEmpty()) return;

        associateRepository.updateAssociatesIsPaidByIds(associatesWithExpiredMonthlyFee, false);
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
        List<ExceptionResponse.Field> errors = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem e se não são do próprio associado
        Associate associateCpf = associateRepository.findByCpf(dto.cpf());
        if (associateCpf != null && !Objects.equals(associateCpf.getId(), id)) {
            log.error("CPF already exists in another associate");
            addError(errors, "CPF already exists", "cpf");
        }

        Associate associateUnionCard = associateRepository.findByUnionCard(dto.unionCard());
        if (associateUnionCard != null && !Objects.equals(associateUnionCard.getId(), id)) {
            log.error("Union Card already exists in another associate");
            addError(errors, "Union Card already exists", "unionCard");
        }

        Associate associateRg = associateRepository.findByRg(dto.rg());
        if (associateRg != null && !Objects.equals(associateRg.getId(), id)) {
            log.error("RG already exists in another associate");
            addError(errors, "RG already exists", "rg");
        }

        if (!errors.isEmpty()) {
            throw new ApiException(ErrorEnum.INVALID_ASSOCIATE, errors);
        }
    }

    private void validateAssociateCreation(AssociatePostDto dto) {
        List<ExceptionResponse.Field> errors = new ArrayList<>();

        // Valida se o cpf, matrícula e rg já existem
        if (associateRepository.findByCpf(dto.cpf()) != null) {
            log.error("CPF already exists in another associate");
            addError(errors, "CPF already exists", "cpf");
        }

        if (associateRepository.findByUnionCard(dto.unionCard()) != null) {
            log.error("Union Card already exists in another associate");
            addError(errors, "Union Card already exists", "unionCard");
        }

        if (associateRepository.findByRg(dto.rg()) != null) {
            log.error("RG already exists in another associate");
            addError(errors, "RG already exists", "rg");
        }

        if (!errors.isEmpty()) {
            throw new ApiException(ErrorEnum.INVALID_ASSOCIATE, errors);
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

    private void addError(List<ExceptionResponse.Field> errors, String message, String field) {
        errors.add(new ExceptionResponse.Field(message, field));
    }
}
