package com.ifba.educampo.service.impl;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.syndicate.SyndicatePostDto;
import com.ifba.educampo.dto.syndicate.SyndicatePutDto;
import com.ifba.educampo.dto.syndicate.SyndicateResponseDto;
import com.ifba.educampo.entity.Address;
import com.ifba.educampo.entity.Syndicate;
import com.ifba.educampo.enums.ErrorsEnum;
import com.ifba.educampo.exception.ApiException;
import com.ifba.educampo.mapper.SyndicateMapper;
import com.ifba.educampo.repository.SyndicateRepository;
import com.ifba.educampo.service.AddressService;
import com.ifba.educampo.service.SyndicateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class SyndicateServiceImpl implements SyndicateService {
    private final SyndicateRepository syndicateRepository;
    private final SyndicateMapper syndicateMapper;
    private final AddressService addressService;

    public SyndicateResponseDto find() {
        log.info("Finding syndicate");
        return syndicateMapper.toResponseDto(syndicateRepository.find()
                .orElseThrow(() -> {
                    log.error("Syndicate not found");
                    return new ApiException(ErrorsEnum.SYNDICATE_NOT_FOUND);
                })
        );
    }

    public SyndicateResponseDto save(SyndicatePostDto dto) {
        Optional<Syndicate> syndicateExits = syndicateRepository.find();
        if (syndicateExits.isPresent()) {
            log.error("Syndicate already exists");
            throw new ApiException(ErrorsEnum.SYNDICATE_ALREADY_EXISTS);
        }

        log.info("Saving syndicate: {}", dto);
        Syndicate syndicate = syndicateRepository.save(syndicateMapper.postDtoToEntity(dto));

        log.info("Saving address: {}", dto.address());
        Address address = addressService.save(dto.address());
        syndicate.setAddress(address);

        return syndicateMapper.toResponseDto(syndicate);
    }

    public SyndicateResponseDto update(SyndicatePutDto dto) {
        log.info("Updating syndicate: {}", dto);
        Syndicate syndicate = syndicateRepository.getReferenceById(find().id());
        syndicate.update(syndicateMapper.putDtoToEntity(dto));

        if (dto.address() != null) {
            log.info("Updating address: {}", dto.address());
            Address address = addressService.update(syndicate.getAddress().getId(), dto.address());
            syndicate.setAddress(address);
        }

        return syndicateMapper.toResponseDto(syndicate);
    }
}
