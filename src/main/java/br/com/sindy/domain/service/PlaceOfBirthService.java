package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import br.com.sindy.domain.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import br.com.sindy.domain.entity.associate.PlaceOfBirth;

public interface PlaceOfBirthService {
    PlaceOfBirth save(PlaceOfBirthPostDto dto);

    PlaceOfBirth update(Long id, PlaceOfBirthPutDto dto);

    void delete(Long id);
}
