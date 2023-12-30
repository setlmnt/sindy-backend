package com.ifba.educampo.service;

import com.ifba.educampo.dto.associate.dependents.DependentsPostDto;
import com.ifba.educampo.dto.associate.dependents.DependentsPutDto;
import com.ifba.educampo.entity.associate.Dependents;

public interface DependentsService {
    Dependents save(DependentsPostDto dto);

    Dependents update(Long id, DependentsPutDto dto);

    void delete(Long id);
}
