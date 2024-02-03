package com.ifba.educampo.domain.service.impl;

import com.ifba.educampo.core.annotation.Log;
import com.ifba.educampo.domain.dto.associate.AssociateResponseDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficePostDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficePutDto;
import com.ifba.educampo.domain.dto.localOffice.LocalOfficeResponseDto;
import com.ifba.educampo.domain.entity.LocalOffice;
import com.ifba.educampo.domain.entity.associate.Associate;
import com.ifba.educampo.domain.enums.ErrorsEnum;
import com.ifba.educampo.domain.exception.ApiException;
import com.ifba.educampo.domain.mapper.LocalOfficeMapper;
import com.ifba.educampo.domain.mapper.associate.AssociateMapper;
import com.ifba.educampo.domain.repository.LocalOfficeRepository;
import com.ifba.educampo.domain.service.LocalOfficeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class LocalOfficeServiceImpl implements LocalOfficeService {
    private final LocalOfficeMapper localOfficeMapper;
    private final AssociateMapper associateMapper;
    private final LocalOfficeRepository localOfficeRepository;

    public Page<AssociateResponseDto> listAllAssociates(Long id, Pageable pageable) {
        log.info("Listing all associates from local office with ID: {}", id);
        Page<Associate> associates = localOfficeRepository.listAllAssociates(id, pageable)
                .orElseThrow(() -> {
                    log.error("Associates from local office with ID {} not found.", id);
                    return new ApiException(ErrorsEnum.ASSOCIATE_NOT_FOUND);
                });
        return associates.map(associateMapper::toResponseDto);
    }

    public LocalOfficeResponseDto findLocalOffice(Long id) {
        log.info("Finding local office with ID: {}", id);
        LocalOffice localOffice = localOfficeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Local office with ID {} not found.", id);
                    return new ApiException(ErrorsEnum.LOCAL_OFFICE_NOT_FOUND);
                });
        return localOfficeMapper.toResponseDto(localOffice);
    }

    public Page<LocalOfficeResponseDto> listAll(Pageable pageable) {
        log.info("Listing all local offices.");
        return localOfficeRepository.findAllAndDeletedFalse(pageable).map(localOfficeMapper::toResponseDto);
    }

    public LocalOfficeResponseDto save(LocalOfficePostDto dto) {
        log.info("Saving local office.");
        LocalOffice localOffice = localOfficeRepository.save(localOfficeMapper.postDtoToEntity(dto));
        return localOfficeMapper.toResponseDto(localOffice);
    }

    public LocalOfficeResponseDto update(Long id, LocalOfficePutDto dto) {
        log.info("Replacing local office with ID: {}", id);

        LocalOffice localOffice = localOfficeRepository.getReferenceById(id);
        localOffice.update(localOfficeMapper.putDtoToEntity(dto));

        return localOfficeMapper.toResponseDto(localOffice);
    }

    public void delete(Long id) {
        log.info("Deleting local office with ID: {}", id);
        LocalOffice localOffice = localOfficeRepository.getReferenceById(id);
        localOffice.delete();
    }
}
