package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import br.com.sindy.domain.entity.associate.PlaceOfBirth;
import br.com.sindy.domain.mapper.associate.PlaceOfBirthMapper;
import br.com.sindy.domain.repository.PlaceOfBirthRepository;
import br.com.sindy.domain.service.PlaceOfBirthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class PlaceOfBirthServiceImpl implements PlaceOfBirthService {
    private final PlaceOfBirthMapper placeOfBirthMapper;
    private final PlaceOfBirthRepository placeOfBirthRepository;

    public PlaceOfBirth save(PlaceOfBirthPostDto dto) {
        log.info("Saving place of birth.");
        return placeOfBirthRepository.save(placeOfBirthMapper.postDtoToEntity(dto));
    }

    public PlaceOfBirth update(Long id, PlaceOfBirthPutDto dto) {
        log.info("Replacing place of birth with ID: {}", id);
        PlaceOfBirth placeOfBirth = placeOfBirthRepository.getReferenceById(id);
        placeOfBirth.update(placeOfBirthMapper.putDtoToEntity(dto));

        return placeOfBirth;
    }

    public void delete(Long id) {
        log.info("Deleting place of birth with ID: {}", id);
        PlaceOfBirth placeOfBirth = placeOfBirthRepository.getReferenceById(id);
        placeOfBirth.delete();
    }
}
