package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AffiliationDto;
import com.ifba.educampo.model.entity.Affiliation;
import com.ifba.educampo.repository.AffiliationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AffiliationService { // Afiliacao
    private final GenericMapper<AffiliationDto, Affiliation> modelMapper;
    private final AffiliationRepository affiliationRepository;

    public Affiliation findAffiliation(Long id) {
        return affiliationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Affiliation Not Found"));
    }

    public List<Affiliation> listAll() {
        return affiliationRepository.findAll();
    }

    public void delete(long id) {
        affiliationRepository.delete(findAffiliation(id));
    }

    @Transactional
    public Affiliation save(AffiliationDto affiliationDto) {
        return affiliationRepository.save(modelMapper.mapDtoToModel(affiliationDto, Affiliation.class));
    }

    public Affiliation replace(AffiliationDto affiliationDto, Long affiliationId) {
        Affiliation savedAffiliation = findAffiliation(affiliationId);
        affiliationDto.setId(savedAffiliation.getId());

        return affiliationRepository.save(modelMapper.mapDtoToModel(affiliationDto, Affiliation.class));
    }
}
