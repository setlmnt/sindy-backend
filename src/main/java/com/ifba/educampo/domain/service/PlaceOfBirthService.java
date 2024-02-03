package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import com.ifba.educampo.domain.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import com.ifba.educampo.domain.entity.associate.PlaceOfBirth;

public interface PlaceOfBirthService {
    PlaceOfBirth save(PlaceOfBirthPostDto dto);

    PlaceOfBirth update(Long id, PlaceOfBirthPutDto dto);

    void delete(Long id);
}
