package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.associate.dependents.DependentsPostDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsPutDto;
import br.com.sindy.domain.entity.associate.Dependents;

public interface DependentsService {
    Dependents save(DependentsPostDto dto);

    Dependents update(Long id, DependentsPutDto dto);

    void delete(Long id);
}
