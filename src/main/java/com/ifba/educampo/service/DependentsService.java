package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.DependentsDto;
import com.ifba.educampo.model.entity.Dependents;
import com.ifba.educampo.repository.DependentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DependentsService { // Dependentes
    private final GenericMapper<DependentsDto, Dependents> modelMapper;
    private final DependentsRepository dependentsRepository;

    public Dependents findDependent(Long id) {
        return dependentsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dependent Not Found"));
    }

    public List<Dependents> listAll() {
        return dependentsRepository.findAll();
    }

    public void delete(long id) {
        dependentsRepository.delete(findDependent(id));
    }

    @Transactional
    public Dependents save(DependentsDto dependentsDto) {
        return dependentsRepository.save(modelMapper.mapDtoToModel(dependentsDto, Dependents.class));
    }

    public Dependents replace(DependentsDto dependentsDto, Long dependentId) {
        Dependents savedDependents = findDependent(dependentId);
        dependentsDto.setId(savedDependents.getId());

        return dependentsRepository.save(modelMapper.mapDtoToModel(dependentsDto, Dependents.class));
    }
}
