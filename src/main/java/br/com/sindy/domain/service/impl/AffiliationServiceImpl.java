package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationPostDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationPutDto;
import br.com.sindy.domain.entity.associate.Affiliation;
import br.com.sindy.domain.mapper.associate.AffiliationMapper;
import br.com.sindy.domain.repository.AffiliationRepository;
import br.com.sindy.domain.service.AffiliationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Log
public class AffiliationServiceImpl implements AffiliationService {
    private final AffiliationMapper affiliationMapper;
    private final AffiliationRepository affiliationRepository;

    public Affiliation save(AffiliationPostDto dto) {
        log.info("Saving affiliation.");
        return affiliationRepository.save(affiliationMapper.postDtoToEntity(dto));
    }

    public Affiliation update(Long id, AffiliationPutDto dto) {
        log.info("Replacing affiliation with ID: {}", id);

        Affiliation affiliation = affiliationRepository.getReferenceById(id);
        affiliation.update(affiliationMapper.putDtoToEntity(dto));

        return affiliation;
    }

    public void delete(Long id) {
        log.info("Deleting affiliation with ID: {}", id);
        Affiliation affiliation = affiliationRepository.getReferenceById(id);
        affiliation.delete();
    }
}
