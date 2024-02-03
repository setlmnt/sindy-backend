package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.affiliation.AffiliationPostDto;
import com.ifba.educampo.domain.dto.associate.affiliation.AffiliationPutDto;
import com.ifba.educampo.domain.entity.associate.Affiliation;

public interface AffiliationService {
    Affiliation save(AffiliationPostDto dto);

    Affiliation update(Long id, AffiliationPutDto dto);

    void delete(Long id);
}
