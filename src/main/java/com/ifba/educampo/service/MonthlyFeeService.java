package com.ifba.educampo.service;

import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePostDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePutDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeeResponseDto;
import com.ifba.educampo.dto.monthlyFee.date.MonthlyFeeDateResponseDto;
import com.ifba.educampo.exception.BadRequestListException;
import com.ifba.educampo.exception.ErrorType;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.associate.AssociateMapper;
import com.ifba.educampo.mapper.monthlyFee.MonthlyFeeDateMapper;
import com.ifba.educampo.mapper.monthlyFee.MonthlyFeeMapper;
import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.model.entity.MonthlyFeeDate;
import com.ifba.educampo.model.entity.associate.Associate;
import com.ifba.educampo.repository.MonthlyFeeCustomRepository;
import com.ifba.educampo.repository.MonthlyFeeDateRepository;
import com.ifba.educampo.repository.MonthlyFeeRepository;
import com.ifba.educampo.service.associate.AssociateService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MonthlyFeeService {
    private final MonthlyFeeMapper monthlyFeeMapper;
    private final MonthlyFeeDateMapper monthlyFeeDateModelMapper;
    private final MonthlyFeeDateRepository monthlyFeeDateRepository;
    private final MonthlyFeeCustomRepository monthlyFeeCustomRepository;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;
    private final AssociateMapper associateMapper;
    private final PdfService pdfService;

    public Page<MonthlyFeeResponseDto> listAll(Integer paymentMonth, Integer paymentYear, Pageable pageable) {
        log.info("Listing all monthly fees.");
        Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
                .findAllFromPaymentMonthAndYearAndDeletedFalse(paymentMonth, paymentYear, pageable);
        return monthlyFees.map(monthlyFeeMapper::toResponseDto);
    }

    public MonthlyFeeResponseDto findMonthlyFee(Long id) {
        MonthlyFee monthlyFee = monthlyFeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Monthly fee with ID {} not found.", id);
                    return new NotFoundException("Monthly Fee Not Found");
                });

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    public Page<MonthlyFeeResponseDto> listAllByAssociateIdAndMonthAndYear(
            Long associateId,
            Integer paymentMonth,
            Integer paymentYear,
            Pageable pageable
    ) {
        log.info("Finding monthly fee by associate ID {}.", associateId);
        Page<MonthlyFee> monthlyFees = monthlyFeeCustomRepository
                .findAllFromAssociateIdPaymentMonthAndYearAndDeletedFalse(associateId, paymentMonth, paymentYear, pageable);
        return monthlyFees.map(monthlyFeeMapper::toResponseDto);
    }

    public MonthlyFeeResponseDto save(MonthlyFeePostDto dto) {
        log.info("Saving monthly fee.");

        AssociateResponseDto associateResponseDto = associateService.findAssociate(dto.associateId());
        Associate associate = associateMapper.responseDtoToEntityWithId(associateResponseDto);

        MonthlyFee monthlyFee = monthlyFeeMapper.postDtoToEntity(dto);
        monthlyFee.setAssociate(associate);

        ifMonthlyFeeIsBeforeAssociationDateThrow(associate.getAssociationAt(), monthlyFee);
        ifMonthlyFeeAlreadyExistsThrow(monthlyFee);

        monthlyFee.setAssociate(associate);

        MonthlyFee finalMonthlyFee = monthlyFee;
        monthlyFee.getPaymentDates().forEach(date -> date.setMonthlyFee(finalMonthlyFee));
        monthlyFee.setTotalAmount(
                dto.feeValue()
                        .multiply(BigDecimal.valueOf(dto.paymentDates().size()))
        );
        monthlyFee.setTotalMonthsPaid(dto.paymentDates().size());

        monthlyFee = monthlyFeeRepository.save(monthlyFee);


        monthlyFeeDateRepository.saveAll(monthlyFee.getPaymentDates());

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    public MonthlyFeeResponseDto update(Long id, MonthlyFeePutDto dto) {
        log.info("Replacing monthly fee with ID: {}", id);

        Associate associate = findAssociateById(id);
        MonthlyFee entity = monthlyFeeMapper.putDtoToEntity(dto);

        ifMonthlyFeeIsBeforeAssociationDateThrow(associate.getAssociationAt(), entity);
        ifMonthlyFeeAlreadyExistsThrow(entity);

        MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);
        MonthlyFee updatedMonthlyFee = getUpdatedMonthlyFee(dto, monthlyFee);
        monthlyFee.update(updatedMonthlyFee);

        return monthlyFeeMapper.toResponseDto(monthlyFee);
    }

    private MonthlyFee getUpdatedMonthlyFee(MonthlyFeePutDto dto, MonthlyFee monthlyFee) {
        MonthlyFee updatedMonthlyFee = monthlyFeeMapper.putDtoToEntity(dto);
        Set<MonthlyFeeDate> monthlyFeeDates = dto.paymentDates()
                .stream().map(monthlyFeeDateModelMapper::dtoToEntity).collect(Collectors.toSet());
        updatedMonthlyFee.setPaymentDates(monthlyFeeDates);
        updatedMonthlyFee.getPaymentDates().forEach(monthlyFeeDate -> monthlyFeeDate.setMonthlyFee(monthlyFee));

        updatedMonthlyFee.setTotalAmount(
                dto.feeValue()
                        .multiply(BigDecimal.valueOf(dto.paymentDates().size()))
        );
        updatedMonthlyFee.setTotalMonthsPaid(dto.paymentDates().size());
        updatedMonthlyFee.setFeeValue(dto.feeValue());
        return updatedMonthlyFee;
    }

    public Associate findAssociateById(Long id) {
        return monthlyFeeRepository.findAssociateByIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("Monthly fee with ID {} not found.", id);
                    return new NotFoundException("Monthly Fee Not Found");
                });
    }

    public void delete(Long id) {
        log.info("Deleting monthly fee with ID: {}", id);
        MonthlyFee monthlyFee = monthlyFeeRepository.getReferenceById(id);
        monthlyFee.delete();
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

    private void ifMonthlyFeeAlreadyExistsThrow(MonthlyFee monthlyFee) {
        List<ErrorType> errors = new ArrayList<>();

        // Garantir que a mensalidade não existe para o associado no mês e ano
        for (MonthlyFeeDate monthlyFeeDate : monthlyFee.getPaymentDates()) {
            Optional<MonthlyFee> monthlyFeeOptional = monthlyFeeRepository.findByAssociateIdAndPaymentMonthAndPaymentYear(
                    monthlyFee.getAssociate().getId(),
                    monthlyFeeDate.getMonth(),
                    monthlyFeeDate.getYear()
            );

            if (monthlyFeeOptional.isPresent() && !monthlyFeeOptional.get().getId().equals(monthlyFee.getId())) {
                errors.add(new ErrorType(
                        "Monthly Fee already exists for this associate (" + monthlyFee.getAssociate().getId() + ") in this month (" + monthlyFeeDate.getMonth() + ") and year (" + monthlyFeeDate.getYear() + ")",
                        "paymentDates")
                );
                break;
            }
        }

        if (!errors.isEmpty()) {
            log.error("Monthly fee already exists for this associate in this month and year.");
            throw new BadRequestListException("Monthly fee already exists for this associate in this month and year.", errors);
        }
    }

    private void ifMonthlyFeeIsBeforeAssociationDateThrow(LocalDate associationDate, MonthlyFee monthlyFee) {
        List<ErrorType> errors = new ArrayList<>();

        for (MonthlyFeeDate monthlyFeeDate : monthlyFee.getPaymentDates()) {
            if (isMonthlyFeeBeforeAssociationDate(associationDate, monthlyFeeDate)) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/yyyy"); // Define o formato desejado
                LocalDate localDateTime = LocalDate.of(monthlyFeeDate.getYear(), monthlyFeeDate.getMonth(), 1);
                String formattedMonthlyFeeDate = localDateTime.format(dateFormatter); // Formata a data
                String formattedAssociationDate = associationDate.format(dateFormatter); // Formata a data

                errors.add(new ErrorType(
                        "Payment Date must be after the association date. " +
                                formattedMonthlyFeeDate + " is before " + formattedAssociationDate + ".",
                        "paymentDates")
                );
            }
        }

        if (!errors.isEmpty()) throw new BadRequestListException("Invalid Monthly Fee", errors);
    }

    private boolean isMonthlyFeeBeforeAssociationDate(LocalDate associationDate, MonthlyFeeDate monthlyFeeDate) {
        LocalDate localDateTime = LocalDate.of(monthlyFeeDate.getYear(), monthlyFeeDate.getMonth(), 1);
        return localDateTime.isBefore(associationDate) &&
                (
                        !associationDate.getMonth().equals(localDateTime.getMonth()) ||
                                associationDate.getYear() != localDateTime.getYear()
                );
    }
}
