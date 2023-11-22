package com.ifba.educampo.service.associate;

import com.ifba.educampo.dto.associate.dependents.DependentsPostDto;
import com.ifba.educampo.dto.associate.dependents.DependentsPutDto;
import com.ifba.educampo.mapper.associate.DependentsMapper;
import com.ifba.educampo.model.entity.associate.Dependents;
import com.ifba.educampo.repository.associate.DependentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DependentsService { // Dependentes
    private final DependentsMapper dependentsMapper;
    private final DependentsRepository dependentsRepository;

    public Page<Dependents> listAll(Pageable pageable) {
        log.info("Listing all dependents.");
        return dependentsRepository.findAll(pageable);
    }

    public Dependents save(DependentsPostDto dto) {
        log.info("Saving dependent.");
        return dependentsRepository.save(dependentsMapper.postDtoToEntity(dto));
    }

    public Dependents update(Long id, DependentsPutDto dto) {
        log.info("Replacing dependent with ID: {}", id);

        Dependents dependents = dependentsRepository.getReferenceById(id);
        dependents.update(dependentsMapper.putDtoToEntity(dto));

        return dependents;
    }

    public void delete(Long id) {
        log.info("Deleting dependent with ID: {}", id);
        Dependents dependents = dependentsRepository.getReferenceById(id);
        dependents.delete();
    }
}
