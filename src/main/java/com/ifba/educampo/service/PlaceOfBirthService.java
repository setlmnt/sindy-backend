package com.ifba.educampo.service;

import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPostDto;
import com.ifba.educampo.dto.associate.placeOfBirth.PlaceOfBirthPutDto;
import com.ifba.educampo.entity.associate.PlaceOfBirth;

public interface PlaceOfBirthService {
    PlaceOfBirth save(PlaceOfBirthPostDto dto);

    PlaceOfBirth update(Long id, PlaceOfBirthPutDto dto);

    void delete(Long id);
}
