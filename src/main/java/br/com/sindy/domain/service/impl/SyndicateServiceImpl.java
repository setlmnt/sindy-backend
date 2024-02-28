package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.syndicate.SyndicatePostDto;
import br.com.sindy.domain.dto.syndicate.SyndicatePutDto;
import br.com.sindy.domain.dto.syndicate.SyndicateResponseDto;
import br.com.sindy.domain.entity.Address;
import br.com.sindy.domain.entity.Syndicate;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.mapper.SyndicateMapper;
import br.com.sindy.domain.repository.SyndicateRepository;
import br.com.sindy.domain.service.AddressService;
import br.com.sindy.domain.service.SyndicateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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
                    return new ApiException(ErrorEnum.SYNDICATE_NOT_FOUND);
                })
        );
    }

    @Transactional
    public SyndicateResponseDto save(SyndicatePostDto dto) {
        Optional<Syndicate> syndicateExits = syndicateRepository.find();
        if (syndicateExits.isPresent()) {
            log.error("Syndicate already exists");
            throw new ApiException(ErrorEnum.SYNDICATE_ALREADY_EXISTS);
        }

        log.info("Saving syndicate: {}", dto);
        Syndicate syndicate = syndicateRepository.save(syndicateMapper.postDtoToEntity(dto));

        log.info("Saving address: {}", dto.address());
        Address address = addressService.save(dto.address());
        syndicate.setAddress(address);

        return syndicateMapper.toResponseDto(syndicate);
    }

    @Transactional
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
