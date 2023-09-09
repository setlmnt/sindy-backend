package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.PlaceOfBirthDto;
import com.ifba.educampo.model.entity.PlaceOfBirth;
import com.ifba.educampo.repository.PlaceOfBirthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceOfBirthService { // Naturalidade
    private final GenericMapper<PlaceOfBirthDto, PlaceOfBirth> modelMapper;
    private final PlaceOfBirthRepository placeOfBirthRepository;

    public PlaceOfBirth findPlaceOfBirth(Long id) {
        return placeOfBirthRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Place Of Birth Not Found"));
    }

    public List<PlaceOfBirth> listAll() {
        return placeOfBirthRepository.findAll();
    }

    public void delete(long id) {
        placeOfBirthRepository.delete(findPlaceOfBirth(id));
    }

    @Transactional
    public PlaceOfBirth save(PlaceOfBirthDto placeOfBirthDto) {
        return placeOfBirthRepository.save(modelMapper.mapDtoToModel(placeOfBirthDto, PlaceOfBirth.class));
    }

    public PlaceOfBirth replace(PlaceOfBirthDto placeOfBirthDto, Long placeOfBirthId) {
        PlaceOfBirth savedPlaceOfBirth = findPlaceOfBirth(placeOfBirthId);
        placeOfBirthDto.setId(savedPlaceOfBirth.getId());

        return placeOfBirthRepository.save(modelMapper.mapDtoToModel(placeOfBirthDto, PlaceOfBirth.class));
    }
}
