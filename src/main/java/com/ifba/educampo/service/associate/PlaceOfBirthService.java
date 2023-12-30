package com.ifba.educampo.service.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import com.ifba.educampo.entity.associate.PlaceOfBirth;
import com.ifba.educampo.mapper.associate.PlaceOfBirthMapper;
import com.ifba.educampo.repository.PlaceOfBirthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class PlaceOfBirthService {
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
