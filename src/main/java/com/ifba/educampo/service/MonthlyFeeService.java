package com.ifba.educampo.service;

import com.ifba.educampo.domain.MonthlyFee;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.MonthlyFeeRepository;
import com.ifba.educampo.requests.MonthlyFeePostRequestBody;
import com.ifba.educampo.requests.MonthlyFeePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthlyFeeService {
    private final MonthlyFeeRepository monthlyFeeRepository;
    private final AssociateService associateService;

    public Page<MonthlyFee> listAll(Pageable pageable) {
        return monthlyFeeRepository.findAll(pageable);
    }

    public Page<MonthlyFee> listAllByMonthAndYear(int paymentMonth, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentYearAndPaymentMonth(paymentYear, paymentMonth, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByYear(int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentYear(paymentYear, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByMonth(int paymentMonth, Pageable pageable) {
        return monthlyFeeRepository.findAllByPaymentMonth(paymentMonth, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public MonthlyFee findMonthlyFee(long id) {
        return monthlyFeeRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateId(long associateId, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateId(associateId, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Optional<MonthlyFee> findMonthlyFeeByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear) {
        return monthlyFeeRepository.findByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear);
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonthAndYear(long associateId, int paymentMonth, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonthAndPaymentYear(associateId, paymentMonth, paymentYear, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateIdAndYear(long associateId, int paymentYear, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentYear(associateId, paymentYear, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public Page<MonthlyFee> listAllByAssociateIdAndMonth(long associateId, int paymentMonth, Pageable pageable) {
        return monthlyFeeRepository.findAllByAssociateIdAndPaymentMonth(associateId, paymentMonth, pageable)
                .orElseThrow(()-> new BadRequestException("Monthly Fee Not Found"));
    }

    public void delete(long id) {
        monthlyFeeRepository.delete(findMonthlyFee(id));
    }

    public MonthlyFee save(MonthlyFeePostRequestBody monthlyFeePostRequestBody) {
        return monthlyFeeRepository.save(MonthlyFee.builder()
                .paidAmount(monthlyFeePostRequestBody.getPaidAmount())
                .paymentMonth(monthlyFeePostRequestBody.getPaymentMonth())
                .paymentYear(monthlyFeePostRequestBody.getPaymentYear())
                .associate(associateService.findAssociate(monthlyFeePostRequestBody.getAssociateId()))
                .build());
    }

    public void replace(MonthlyFeePutRequestBody monthlyFeePutRequestBody) {
        MonthlyFee savedMonthlyFee = findMonthlyFee(monthlyFeePutRequestBody.getId());
        monthlyFeeRepository.save(MonthlyFee.builder()
                .id(savedMonthlyFee.getId())
                .paidAmount(monthlyFeePutRequestBody.getPaidAmount())
                .paymentMonth(monthlyFeePutRequestBody.getPaymentMonth())
                .paymentYear(monthlyFeePutRequestBody.getPaymentYear())
                .associate(associateService.findAssociate(monthlyFeePutRequestBody.getAssociateId()))
                .build());
    }

    public void updateByFields(long id, Map<String, Object> fields) {
        // TODO Auto-generated method stub
        MonthlyFee savedMonthlyFee = findMonthlyFee(id);

        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(MonthlyFee.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, savedMonthlyFee, value);
        });
        monthlyFeeRepository.save(savedMonthlyFee);
    }
}
