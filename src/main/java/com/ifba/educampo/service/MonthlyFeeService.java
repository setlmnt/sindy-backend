package com.ifba.educampo.service;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePostDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePutDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeeResponseDto;
import com.ifba.educampo.entity.MonthlyFee;
import com.ifba.educampo.entity.associate.Associate;
import com.ifba.educampo.exception.BadRequestListException;
import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.associate.AssociateMapper;
import com.ifba.educampo.mapper.monthlyFee.MonthlyFeeMapper;
import com.ifba.educampo.repository.monthlyFee.MonthlyFeeCustomRepository;
import com.ifba.educampo.repository.monthlyFee.MonthlyFeeRepository;
import com.ifba.educampo.service.associate.AssociateService;
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
public class MonthlyFeeService {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private final MonthlyFeeMapper monthlyFeeMapper;
    private final MonthlyFeeCustomRepository monthlyFeeCustomRepository;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;
    private final AssociateMapper associateMapper;
    private final ReportService reportService;

    private void addError(List<ErrorType> errors, String message, String field) {
        errors.add(new ErrorType(message, field));
    }

    public Page<MonthlyFeeResponseDto> findAll(LocalDate initialDate, LocalDate finalDate, Pageable pageable) {
        log.info("Listing all monthly fees.");
        Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
                .findAllFromInitialDateAndFinalDate(initialDate, finalDate, pageable);
        return monthlyFees.map(monthlyFeeMapper::toResponseDto);
    }

    public MonthlyFeeResponseDto findById(Long id) {
        MonthlyFee monthlyFee = monthlyFeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Monthly fee with ID {} not found.", id);
                    return new NotFoundException("Monthly Fee Not Found");
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
        Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
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

        JasperReport syndicateReport = reportService.compileReport("syndicate_report");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("monthlyFeeId", id);
        parameters.put("syndicateReport", syndicateReport);

        byte[] report = reportService.generateReport("monthly_fee_report", parameters);

        String fileName = monthlyFee.associate().name() + "-mensalidade.pdf";
        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + fileName.replace(" ", "_")
        );

        return report;
    }

    private void validateUpdateMonthlyFee(MonthlyFee updatedMonthlyFee, MonthlyFee monthlyFee, Associate associate) {
        if (updatedMonthlyFee.getFinalDate() != null && !updatedMonthlyFee.getFinalDate().isEqual(monthlyFee.getFinalDate())) {
            List<ErrorType> errors = new ArrayList<>();
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

            if (!errors.isEmpty()) throw new BadRequestListException("Invalid Monthly Fee", errors);
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
        List<ErrorType> errors = new ArrayList<>();
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

        if (!errors.isEmpty()) throw new BadRequestListException("Invalid Monthly Fee", errors);
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
}
