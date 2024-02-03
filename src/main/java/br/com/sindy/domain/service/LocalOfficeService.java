package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.AssociateResponseDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficePostDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficePutDto;
import br.com.sindy.domain.dto.localOffice.LocalOfficeResponseDto;
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
