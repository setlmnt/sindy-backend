package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.AssociatePostDto;
import br.com.sindy.domain.dto.associate.AssociatePutDto;
import br.com.sindy.domain.dto.associate.AssociateResponseDto;
import br.com.sindy.domain.enums.PeriodEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssociateService {
    Page<AssociateResponseDto> findAll(String query, Pageable pageable);

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
