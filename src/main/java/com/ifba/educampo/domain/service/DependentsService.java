package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.associate.dependents.DependentsPostDto;
import com.ifba.educampo.domain.dto.associate.dependents.DependentsPutDto;
import com.ifba.educampo.domain.entity.associate.Dependents;

public interface DependentsService {
    Dependents save(DependentsPostDto dto);

    Dependents update(Long id, DependentsPutDto dto);

    void delete(Long id);
}
