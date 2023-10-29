package com.ifba.educampo.service;

import com.ifba.educampo.dto.MonthlyFeeDateDto;
import com.ifba.educampo.dto.MonthlyFeeDto;
import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.MonthlyFeeException;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.model.entity.MonthlyFeeDate;
import com.ifba.educampo.repository.MonthlyFeeCustomRepository;
import com.ifba.educampo.repository.MonthlyFeeRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MonthlyFeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonthlyFeeService.class);
    private final GenericMapper<MonthlyFeeDto, MonthlyFee> modelMapper;
    private final GenericMapper<MonthlyFeeDateDto, MonthlyFeeDate> monthlyFeeDateModelMapper;
    private final MonthlyFeeCustomRepository monthlyFeeCustomRepository;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;
    private final PdfService pdfService;

    public Page<MonthlyFeeDto> listAll(Integer paymentMonth, Integer paymentYear, Pageable pageable) {
        try {
            LOGGER.info("Listing all monthly fees.");
            Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
                    .findAllFromPaymentMonthAndYearAndDeletedFalse(paymentMonth, paymentYear, pageable);

            return monthlyFees.map(monthlyFee -> {
                MonthlyFeeDto monthlyFeeDto = modelMapper.mapModelToDto(monthlyFee, MonthlyFeeDto.class);
                monthlyFeeDto.setAssociateId(monthlyFee.getAssociate().getId());
                return monthlyFeeDto;
            });
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing monthly fees.", e);
            throw new NotFoundException("An error occurred while listing monthly fees.");
        }
    }

    public MonthlyFeeDto findMonthlyFee(Long id) {
        MonthlyFee monthlyFee = monthlyFeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    LOGGER.error("Monthly fee with ID {} not found.", id);
                    return new NotFoundException("Monthly Fee Not Found");
                });

        MonthlyFeeDto monthlyFeeDto = modelMapper.mapModelToDto(monthlyFee, MonthlyFeeDto.class);
        monthlyFeeDto.setAssociateId(monthlyFee.getAssociate().getId());
        return monthlyFeeDto;
    }

    public Page<MonthlyFeeDto> listAllByAssociateIdAndMonthAndYear(
            Long associateId,
            Integer paymentMonth,
            Integer paymentYear,
            Pageable pageable
    ) {
        try {
            LOGGER.info("Finding monthly fee by associate ID {}.", associateId);
            Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
                    .findAllFromAssociateIdPaymentMonthAndYearAndDeletedFalse(associateId, paymentMonth, paymentYear, pageable);

            return monthlyFees.map(monthlyFee -> {
                MonthlyFeeDto monthlyFeeDto = modelMapper.mapModelToDto(monthlyFee, MonthlyFeeDto.class);
                monthlyFeeDto.setAssociateId(monthlyFee.getAssociate().getId());
                return monthlyFeeDto;
            });
        } catch (Exception e) {
            LOGGER.error("An error occurred while finding monthly fee by associate ID {}.", associateId, e);
            throw new NotFoundException("An error occurred while finding monthly fee by associate ID {} and month {} and year {}.");
        }
    }

    public void delete(Long id) {
        try {
            LOGGER.info("Deleting monthly fee with ID: {}", id);
            MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);
            monthlyFee.delete();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing monthly fees.", e);
            throw new NotFoundException("An error occurred while deleting monthly fee.");
        }
    }

    public MonthlyFeeDto save(MonthlyFeeDto monthlyFeeDto) {
        try {
            LOGGER.info("Saving monthly fee.");

            Associate associate = associateService.findAssociate(monthlyFeeDto.getAssociateId());

            ifMonthlyFeeDateAreTheSameThrow(monthlyFeeDto);
            ifMonthlyFeeIsBeforeAssociationDateThrow(associate.getAssociationAt(), monthlyFeeDto);
            ifMonthlyFeeAlreadyExistsThrow(monthlyFeeDto);

            MonthlyFee monthlyFee = modelMapper.mapDtoToModel(monthlyFeeDto, MonthlyFee.class);

            monthlyFee.getPaymentDates().forEach(monthlyFeeDate -> monthlyFeeDate.setMonthlyFee(monthlyFee));
            monthlyFee.setAssociate(associate);

            monthlyFee.setTotalAmount(
                    monthlyFeeDto
                            .getFeeValue()
                            .multiply(BigDecimal.valueOf(monthlyFeeDto.getPaymentDates().size()))
            );
            monthlyFee.setTotalMonthsPaid(monthlyFeeDto.getPaymentDates().size());

            monthlyFeeDto = modelMapper.mapModelToDto(monthlyFeeRepository.save(monthlyFee), MonthlyFeeDto.class);
            monthlyFeeDto.setAssociateId(associate.getId());
            return monthlyFeeDto;
        } catch (MonthlyFeeException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("An error occurred while saving monthly fee.", e);
            throw new NotFoundException("An error occurred while saving monthly fee.");
        }
    }

    public MonthlyFeeDto update(Long id, MonthlyFeeDto monthlyFeeDto) {
        try {
            LOGGER.info("Replacing monthly fee with ID: {}", id);

            Associate associate = associateService.findAssociate(monthlyFeeDto.getAssociateId());

            ifMonthlyFeeDateAreTheSameThrow(monthlyFeeDto);
            ifMonthlyFeeIsBeforeAssociationDateThrow(associate.getAssociationAt(), monthlyFeeDto);
            ifMonthlyFeeAlreadyExistsThrow(monthlyFeeDto);

            MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);

            List<MonthlyFeeDate> monthlyFeeDates = monthlyFeeDateModelMapper.mapList(monthlyFeeDto.getPaymentDates(), MonthlyFeeDate.class);
            monthlyFee.setPaymentDates(monthlyFeeDates);
            monthlyFee.getPaymentDates().forEach(monthlyFeeDate -> monthlyFeeDate.setMonthlyFee(monthlyFee));
            monthlyFee.setAssociate(associate);

            monthlyFee.setTotalAmount(
                    monthlyFeeDto
                            .getFeeValue()
                            .multiply(BigDecimal.valueOf(monthlyFeeDto.getPaymentDates().size()))
            );
            monthlyFee.setTotalMonthsPaid(monthlyFeeDto.getPaymentDates().size());
            monthlyFee.setFeeValue(monthlyFeeDto.getFeeValue());

            monthlyFeeDto = modelMapper.mapModelToDto(monthlyFee, MonthlyFeeDto.class);
            monthlyFeeDto.setAssociateId(associate.getId());
            return monthlyFeeDto;
        } catch (Exception e) {
            LOGGER.error("An error occurred while replacing monthly fee.", e);
            throw new NotFoundException("An error occurred while replacing monthly fee.");
        }
    }

    public byte[] exportToPdf(Long id, HttpServletResponse response) {
        Optional<MonthlyFee> monthlyFee = monthlyFeeRepository.findById(id);

        if (monthlyFee.isEmpty()) return null;

        response.setContentType("application/pdf");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + monthlyFee.get().getAssociate().getName() + "-mensalidade.pdf"
        );

        Context context = new Context();
        context.setVariable("monthlyFee", monthlyFee);

        return pdfService.generatePdfByTemplate("monthly-fee", context);
    }

    private void ifMonthlyFeeDateAreTheSameThrow(MonthlyFeeDto monthlyFeeDto) {
        List<ErrorType> errors = new ArrayList<>();

        for (int i = 1; i < monthlyFeeDto.getPaymentDates().size(); i++) {
            if (monthlyFeeDto.getPaymentDates().get(0).equals(monthlyFeeDto.getPaymentDates().get(i))) {
                errors.add(new ErrorType(
                        "Payment Date must be different.",
                        "paymentDates")
                );
                break;
            }
        }

        if (!errors.isEmpty()) {
            LOGGER.error("Payment Date must be different.");
            throw new MonthlyFeeException("Invalid Monthly Fee", errors);
        }
    }

    private void ifMonthlyFeeAlreadyExistsThrow(MonthlyFeeDto monthlyFeeDto) {
        List<ErrorType> errors = new ArrayList<>();

        // Garantir que a mensalidade não existe para o associado no mês e ano
        for (MonthlyFeeDateDto monthlyFeeDateDto : monthlyFeeDto.getPaymentDates()) {
            Optional<MonthlyFee> monthlyFeeOptional = monthlyFeeRepository.findByAssociateIdAndPaymentMonthAndPaymentYear(
                    monthlyFeeDto.getAssociateId(),
                    monthlyFeeDateDto.getMonth(),
                    monthlyFeeDateDto.getYear()
            );

            if (monthlyFeeOptional.isPresent() && !monthlyFeeOptional.get().getId().equals(monthlyFeeDto.getId())) {
                errors.add(new ErrorType(
                        "Monthly Fee already exists for this associate (" + monthlyFeeDto.getAssociateId() + ") in this month (" + monthlyFeeDateDto.getMonth() + ") and year (" + monthlyFeeDateDto.getYear() + ")",
                        "paymentDates")
                );
                break;
            }
        }

        if (!errors.isEmpty()) {
            LOGGER.error("Monthly fee already exists for this associate in this month and year.");
            throw new MonthlyFeeException("Monthly fee already exists for this associate in this month and year.", errors);
        }
    }

    private void ifMonthlyFeeIsBeforeAssociationDateThrow(LocalDateTime associationDate, MonthlyFeeDto monthlyFeeDto) {
        List<ErrorType> errors = new ArrayList<>();

        for (MonthlyFeeDateDto monthlyFeeDateDto : monthlyFeeDto.getPaymentDates()) {
            if (isMonthlyFeeBeforeAssociationDate(associationDate, monthlyFeeDateDto)) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy"); // Define o formato desejado
                LocalDateTime monthlyFeeDate = LocalDateTime.of(monthlyFeeDateDto.getYear(), monthlyFeeDateDto.getMonth(), 1, 0, 0, 0);
                String formattedMonthlyFeeDate = monthlyFeeDate.format(dateFormatter); // Formata a data
                String formattedAssociationDate = associationDate.format(dateFormatter); // Formata a data

                errors.add(new ErrorType(
                        "Payment Date must be after the association date. " +
                                formattedMonthlyFeeDate + " is before " + formattedAssociationDate + ".",
                        "paymentDates")
                );
            }
        }

        if (!errors.isEmpty()) throw new MonthlyFeeException("Invalid Monthly Fee", errors);
    }

    private boolean isMonthlyFeeBeforeAssociationDate(LocalDateTime associationDate, MonthlyFeeDateDto monthlyFeeDateDto) {
        LocalDateTime monthlyFeeDate = LocalDateTime.of(monthlyFeeDateDto.getYear(), monthlyFeeDateDto.getMonth(), 1, 0, 0, 0);
        return monthlyFeeDate.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(monthlyFeeDate.getMonth()) ||
                                associationDate.getYear() != monthlyFeeDate.getYear()
                );
    }
}
