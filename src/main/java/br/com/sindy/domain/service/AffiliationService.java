package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.affiliation.AffiliationPostDto;
import br.com.sindy.domain.dto.associate.affiliation.AffiliationPutDto;
import br.com.sindy.domain.entity.associate.Affiliation;

public interface AffiliationService {
    Affiliation save(AffiliationPostDto dto);

    Affiliation update(Long id, AffiliationPutDto dto);

    void delete(Long id);
}
