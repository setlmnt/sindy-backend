package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.AssociateResponseDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePostDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeePutDto;
import br.com.sindy.domain.dto.monthlyFee.MonthlyFeeResponseDto;
import br.com.sindy.domain.entity.MonthlyFee;
import br.com.sindy.domain.entity.Template;
import br.com.sindy.domain.entity.associate.Associate;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.exception.ExceptionResponse;
import br.com.sindy.domain.mapper.associate.AssociateMapper;
import br.com.sindy.domain.mapper.monthlyFee.MonthlyFeeMapper;
import br.com.sindy.domain.repository.MonthlyFeeRepository;
import br.com.sindy.domain.service.MonthlyFeeService;
import br.com.sindy.domain.service.ReportService;
import br.com.sindy.domain.service.TemplateService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@Log
public class MonthlyFeeServiceImpl implements MonthlyFeeService {
    public static final String SYNDICATE_REPORT = "syndicate.report";
    public static final String MONTHLY_FEE_REPORT = "monthly.fee.report";
    public static final String MONTHLY_FEE_SUFFIX = "mensalidade";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final MonthlyFeeMapper monthlyFeeMapper;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateServiceImpl associateService;
    private final AssociateMapper associateMapper;
    private final ReportService reportService;
    private final TemplateService templateService;

    public Page<MonthlyFeeResponseDto> findAll(LocalDate initialDate, LocalDate finalDate, Pageable pageable) {
        log.info("Listing all monthly fees.");
        Page<MonthlyFee> monthlyFees = monthlyFeeRepository
                .findAllFromInitialDateAndFinalDate(initialDate, finalDate, pageable);
        return monthlyFees.map(monthlyFeeMapper::toResponseDto);
    }

    public MonthlyFeeResponseDto findById(Long id) {
        MonthlyFee monthlyFee = monthlyFeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Monthly fee with ID {} not found.", id);
                    return new ApiException(ErrorEnum.MONTHLY_FEE_NOT_FOUND);
                });

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    public Page<MonthlyFeeResponseDto> findAllByAssociateIdAndMonthAndYear(
            Long associateId,
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    ) {
        log.info("Finding monthly fee by associate ID {}.", associateId);
        Page<MonthlyFee> monthlyFees = monthlyFeeRepository
                .findAllFromAssociateIdAndInitialDateAndFinalDate(associateId, initialDate, finalDate, pageable);
        return monthlyFees.map(monthlyFeeMapper::toResponseDto);
    }

    public MonthlyFeeResponseDto save(MonthlyFeePostDto dto) {
        log.info("Saving monthly fee.");

        AssociateResponseDto associateResponseDto = associateService.findById(dto.associateId());
        MonthlyFee monthlyFee = prepareSaveMonthlyFee(dto, associateResponseDto);

        validateSaveMonthlyFee(monthlyFee, associateResponseDto);

        monthlyFee = monthlyFeeRepository.save(monthlyFee);

        if (monthlyFee.getFinalDate().isAfter(LocalDate.now())) {
            associateService.updatePaidStatus(associateResponseDto.id(), true);
        }

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    public MonthlyFeeResponseDto update(Long id, MonthlyFeePutDto dto) {
        log.info("Updating monthly fee with ID: {}", id);

        MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);
        Associate associate = monthlyFee.getAssociate();
        MonthlyFee updatedMonthlyFee = prepareUpdateMonthlyFee(dto, associate);

        validateUpdateMonthlyFee(updatedMonthlyFee, monthlyFee, associate);

        monthlyFee.update(updatedMonthlyFee);

        if (monthlyFee.getFinalDate().isAfter(LocalDate.now())) {
            associateService.updatePaidStatus(associate.getId(), true);
        }

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    public void delete(Long id) {
        log.info("Deleting monthly fee with ID: {}", id);
        MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);
        monthlyFee.delete();
    }

    public byte[] exportToPdf(Long id, HttpServletResponse response) {
        MonthlyFeeResponseDto monthlyFee = findById(id);

        Template syndicateTemplate = templateService.getTemplate(SYNDICATE_REPORT);
        JasperReport syndicateReport = reportService.compileReport(syndicateTemplate);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("monthlyFeeId", id);
        parameters.put("syndicateReport", syndicateReport);

        Template monthlyFeeTemplate = templateService.getTemplate(MONTHLY_FEE_REPORT);
        byte[] report = reportService.generatePdfReport(monthlyFeeTemplate, parameters);

        String fileName = monthlyFee.associate().name().replace(" ", "_") + "-" + MONTHLY_FEE_SUFFIX + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName
        );

        return report;
    }

    private void validateUpdateMonthlyFee(MonthlyFee updatedMonthlyFee, MonthlyFee monthlyFee, Associate associate) {
        if (updatedMonthlyFee.getFinalDate() != null && !updatedMonthlyFee.getFinalDate().isEqual(monthlyFee.getFinalDate())) {
            List<ExceptionResponse.Field> errors = new ArrayList<>();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

            if (updatedMonthlyFee.getFinalDate().isBefore(monthlyFee.getInitialDate()) || updatedMonthlyFee.getFinalDate().isEqual(monthlyFee.getInitialDate())) {
                String formattedFinalDate = updatedMonthlyFee.getFinalDate().format(dateFormatter);
                String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
                addError(errors,
                        "Final date must be after the initial date. " + formattedFinalDate + " is before or equal to " + formattedInitialDate + ".",
                        "Final date"
                );
            }

            MonthlyFee nextMonthlyFee = monthlyFeeRepository.findNextByAssociateId(associate.getId(), monthlyFee.getFinalDate());

            if (nextMonthlyFee != null) {
                if (!updatedMonthlyFee.getFinalDate().isEqual(nextMonthlyFee.getInitialDate())) {
                    String formattedFinalDate = updatedMonthlyFee.getFinalDate().format(dateFormatter);
                    String formattedInitialDate = nextMonthlyFee.getInitialDate().format(dateFormatter);
                    addError(errors,
                            "Final date must be equal to the next monthly fee initial date. " + formattedFinalDate + " is not equal to " + formattedInitialDate + ".",
                            "Final date"
                    );
                }
            }

            if (!errors.isEmpty()) throw new ApiException(ErrorEnum.INVALID_MONTHLY_FEE, errors);
        }
    }

    private MonthlyFee prepareUpdateMonthlyFee(MonthlyFeePutDto dto, Associate associate) {
        MonthlyFee updatedMonthlyFee = monthlyFeeMapper.putDtoToEntity(dto);
        if (updatedMonthlyFee.getFinalDate() != null) {
            LocalDate finalDate = dto.finalDate().withDayOfMonth(associate.getAssociationAt().getDayOfMonth());
            updatedMonthlyFee.setFinalDate(finalDate);
        }
        return updatedMonthlyFee;
    }

    private void validateSaveMonthlyFee(MonthlyFee monthlyFee, AssociateResponseDto associateResponseDto) {
        List<ExceptionResponse.Field> errors = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        if (monthlyFee.getInitialDate().isAfter(monthlyFee.getFinalDate()) || monthlyFee.getInitialDate().isEqual(monthlyFee.getFinalDate())) {
            String formattedFinalDate = monthlyFee.getFinalDate().format(dateFormatter);
            String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
            addError(errors,
                    "Final date must be after the initial date. " + formattedFinalDate + " is before or equal to " + formattedInitialDate + ".",
                    "Final date"
            );
        }

        if (monthlyFee.getInitialDate().isBefore(associateResponseDto.associationAt())) {
            String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
            String formattedAssociationDate = associateResponseDto.associationAt().format(dateFormatter);
            addError(errors,
                    "Initial date must be after the association date. " + formattedInitialDate + " is before " + formattedAssociationDate + ".",
                    "Initial date"
            );
        }

        MonthlyFee lastMonthlyFee = monthlyFeeRepository.findLastByAssociateId(associateResponseDto.id());

        if (lastMonthlyFee != null) {
            if (!monthlyFee.getInitialDate().isEqual(lastMonthlyFee.getFinalDate())) {
                String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
                String formattedFinalDate = lastMonthlyFee.getFinalDate().format(dateFormatter);
                addError(errors,
                        "Initial date must be equal to the last monthly fee final date. " + formattedInitialDate + " is not equal to " + formattedFinalDate + ".",
                        "Initial date"
                );
            }

            if (lastMonthlyFee.getFinalDate().plusMonths(3).isBefore(LocalDate.now())) {
                if (!monthlyFee.getInitialDate().isEqual(lastMonthlyFee.getFinalDate())) {
                    String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
                    String formattedLastMonthlyFeeDate = lastMonthlyFee.getFinalDate().format(dateFormatter);
                    addError(errors,
                            "If the last monthly fee is more than three months before the current date, the initial date must be equal to the last monthly fee final date. " +
                                    formattedInitialDate + " is not equal to " + formattedLastMonthlyFeeDate + ".",
                            "Initial date"
                    );
                }

                if (monthlyFee.getFinalDate().isBefore(LocalDate.now())) {
                    String formattedFinalDate = monthlyFee.getFinalDate().format(dateFormatter);
                    String formattedCurrentLocalDate = LocalDate.now().format(dateFormatter);
                    addError(errors,
                            "If the last monthly fee is more than three months before the current date, the final date must be after the current date. " +
                                    formattedFinalDate + " is before " + formattedCurrentLocalDate + ".",
                            "Final date"
                    );
                }
            }
        } else {
            if (!associateResponseDto.associationAt().isEqual(monthlyFee.getInitialDate())) {
                String formattedInitialDate = monthlyFee.getInitialDate().format(dateFormatter);
                String formattedAssociationDate = associateResponseDto.associationAt().format(dateFormatter);
                addError(errors,
                        "Initial date must be equal to the association date. " + formattedInitialDate + " is not equal to " + formattedAssociationDate + ".",
                        "Initial date"
                );
            }
        }

        if (!errors.isEmpty()) throw new ApiException(ErrorEnum.INVALID_MONTHLY_FEE, errors);
    }

    private MonthlyFee prepareSaveMonthlyFee(MonthlyFeePostDto dto, AssociateResponseDto associateResponseDto) {
        MonthlyFee monthlyFee = monthlyFeeMapper.postDtoToEntity(dto);
        LocalDate initialDate = dto.initialDate().withDayOfMonth(associateResponseDto.associationAt().getDayOfMonth());
        LocalDate finalDate = dto.finalDate().withDayOfMonth(associateResponseDto.associationAt().getDayOfMonth());
        monthlyFee.setInitialDate(initialDate);
        monthlyFee.setFinalDate(finalDate);
        monthlyFee.setTotalMonthsPaid();
        monthlyFee.setTotalFeeValue();
        monthlyFee.setAssociate(associateMapper.responseDtoToEntityWithId(associateResponseDto));
        return monthlyFee;
    }

    private void addError(List<ExceptionResponse.Field> errors, String message, String field) {
        errors.add(new ExceptionResponse.Field(message, field));
    }
}
