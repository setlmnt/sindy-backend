package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.LocalOfficeDto;
import com.ifba.educampo.model.entity.Associate;
import com.ifba.educampo.model.entity.LocalOffice;
import com.ifba.educampo.repository.LocalOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocalOfficeService { // Delegacia (Escritorio Local)
    private final GenericMapper<LocalOfficeDto, LocalOffice> modelMapper;
    private final LocalOfficeRepository localOfficeRepository;

    public Page<Associate> listAllAssociates(long localOfficeId, Pageable pageable) {
        return localOfficeRepository.listAllAssociates(localOfficeId, pageable)
                .orElseThrow(() -> new NotFoundException("Associates Not Found"));
    }

    public LocalOffice findLocalOffice(long id) {
        return localOfficeRepository.findById(id).orElseThrow(() -> new NotFoundException("Local Office Not Found"));
    }

    public Page<LocalOffice> listAll(Pageable pageable) {
        return localOfficeRepository.findAll(pageable);
    }

    public void delete(long id) {
        localOfficeRepository.delete(findLocalOffice(id));
    }

    public LocalOffice save(LocalOfficeDto localOfficeDto) {
        return localOfficeRepository.save(modelMapper.mapDtoToModel(localOfficeDto, LocalOffice.class));
    }

    public LocalOffice replace(LocalOfficeDto localOfficeDto) {
        LocalOffice savedLocalOffice = findLocalOffice(localOfficeDto.getId());
        localOfficeDto.setId(savedLocalOffice.getId());

        return localOfficeRepository.save(modelMapper.mapDtoToModel(localOfficeDto, LocalOffice.class));
    }
}
