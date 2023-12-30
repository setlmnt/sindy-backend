package com.ifba.educampo.service;

import com.ifba.educampo.dto.associate.affiliation.AffiliationPostDto;
import com.ifba.educampo.dto.associate.affiliation.AffiliationPutDto;
import com.ifba.educampo.entity.associate.Affiliation;

public interface AffiliationService {
    Affiliation save(AffiliationPostDto dto);

    Affiliation update(Long id, AffiliationPutDto dto);

    void delete(Long id);
}
