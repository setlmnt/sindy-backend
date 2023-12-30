package com.ifba.educampo.service;

import com.ifba.educampo.dto.monthlyFee.MonthlyFeePostDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeePutDto;
import com.ifba.educampo.dto.monthlyFee.MonthlyFeeResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MonthlyFeeService {
    Page<MonthlyFeeResponseDto> findAll(LocalDate initialDate, LocalDate finalDate, Pageable pageable);

    MonthlyFeeResponseDto findById(Long id);

    Page<MonthlyFeeResponseDto> findAllByAssociateIdAndMonthAndYear(
            Long associateId,
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    );

    MonthlyFeeResponseDto save(MonthlyFeePostDto dto);

    MonthlyFeeResponseDto update(Long id, MonthlyFeePutDto dto);

    void delete(Long id);

    byte[] exportToPdf(Long id, HttpServletResponse response);
}
