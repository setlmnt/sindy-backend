package com.ifba.educampo.service.impl;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.associate.AssociateResponseDto;
import com.ifba.educampo.dto.localOffice.LocalOfficePostDto;
import com.ifba.educampo.dto.localOffice.LocalOfficePutDto;
import com.ifba.educampo.dto.localOffice.LocalOfficeResponseDto;
import com.ifba.educampo.entity.LocalOffice;
import com.ifba.educampo.entity.associate.Associate;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.LocalOfficeMapper;
import com.ifba.educampo.mapper.associate.AssociateMapper;
import com.ifba.educampo.repository.LocalOfficeRepository;
import com.ifba.educampo.service.LocalOfficeService;
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
                    return new NotFoundException("Associates Not Found");
                });
        return associates.map(associateMapper::toResponseDto);
    }

    public LocalOfficeResponseDto findLocalOffice(Long id) {
        log.info("Finding local office with ID: {}", id);
        LocalOffice localOffice = localOfficeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Local office with ID {} not found.", id);
                    return new NotFoundException("Local Office Not Found");
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
