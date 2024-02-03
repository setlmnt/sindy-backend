package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.AssociateResponseDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficePostDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficePutDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocalOfficeService {
    Page<AssociateResponseDto> listAllAssociates(Long id, Pageable pageable);

    LocalOfficeResponseDto findLocalOffice(Long id);

    Page<LocalOfficeResponseDto> listAll(Pageable pageable);

    LocalOfficeResponseDto save(LocalOfficePostDto dto);

    void delete(Long id);

    LocalOfficeResponseDto update(Long id, LocalOfficePutDto dto);
}
