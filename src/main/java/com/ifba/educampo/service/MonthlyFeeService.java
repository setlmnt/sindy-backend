package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.MonthlyFeeDto;
import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.repository.MonthlyFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyFeeService {
    private final GenericMapper<MonthlyFeeDto, MonthlyFee> modelMapper;
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;

    public Page<MonthlyFee> listAll(Pageable pageable) {
        return monthlyFeeRepository.findAll(pageable);
    }

    public Page<MonthlyFee> listAllByMonthAndYear(int paymentMonth, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentYearAndPaymentMonth(paymentYear, paymentMonth, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByYear(int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentYear(paymentYear, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByMonth(int paymentMonth, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentMonth(paymentMonth, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public MonthlyFee findMonthlyFee(long id) {
        return monthlyFeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateId(long associateId, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateId(associateId, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Optional<MonthlyFee> findMonthlyFeeByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear) {
        return monthlyFeeRepository.findByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear);
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateIdAndYear(long associateId, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentYear(associateId, paymentYear, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonth(long associateId, int paymentMonth, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonth(associateId, paymentMonth, pageable)
                .orElseThrow(() -> new NotFoundException("Monthly Fee Not Found"));
    }

    public void delete(long id) {
        monthlyFeeRepository.delete(findMonthlyFee(id));
    }

    public MonthlyFee save(MonthlyFeeDto monthlyFeeDto) {
        MonthlyFee monthlyFee = modelMapper.mapDtoToModel(monthlyFeeDto, MonthlyFee.class);
        monthlyFee.setAssociate(associateService.findAssociate(monthlyFeeDto.getAssociateId()));
        return monthlyFeeRepository.save(monthlyFee);
    }

    public void replace(MonthlyFeeDto monthlyFeeDto) {
        MonthlyFee savedMonthlyFee = findMonthlyFee(monthlyFeeDto.getId());
        monthlyFeeDto.setId(savedMonthlyFee.getId());

        monthlyFeeRepository.save(modelMapper.mapDtoToModel(monthlyFeeDto, MonthlyFee.class));
    }
}
