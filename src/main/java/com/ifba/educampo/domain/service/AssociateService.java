package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.AssociatePostDto;
import com.ifba.educampo.domain.dto.associate.AssociatePutDto;
import com.ifba.educampo.domain.dto.associate.AssociateResponseDto;
import com.ifba.educampo.domain.enums.PeriodEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociateService {
    Page<AssociateResponseDto> findAll(String name, String cpf, Long unionCard, Pageable pageable);

    AssociateResponseDto findById(Long id);

    AssociateResponseDto save(AssociatePostDto dto);

    AssociateResponseDto update(Long id, AssociatePutDto dto);

    void delete(Long id);

    void deleteImage(Long associateId);

    Page<AssociateResponseDto> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period);

    void updatePaidStatus(Long id, boolean status);

    byte[] exportAssociateToPdf(Long id, HttpServletResponse response);

    byte[] exportAssociateMembershipCardToPdf(Long id, HttpServletResponse response);

    void setAssociateIsPaidToFalseWhenMonthlyFeeAsAlreadyExpired();
}
