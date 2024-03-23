package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.associate.dependents.DependentsPostDto;
import br.com.sindy.domain.dto.associate.dependents.DependentsPutDto;
import br.com.sindy.domain.entity.associate.Dependents;
import br.com.sindy.domain.mapper.associate.DependentsMapper;
import br.com.sindy.domain.repository.DependentsRepository;
import br.com.sindy.domain.service.DependentsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Log
public class DependentsServiceImpl implements DependentsService {
    private final DependentsMapper dependentsMapper;
    private final DependentsRepository dependentsRepository;

    @Transactional
    public Dependents save(DependentsPostDto dto) {
        log.info("Saving dependent.");
        return dependentsRepository.save(dependentsMapper.postDtoToEntity(dto));
    }

    @Transactional
    public Dependents update(Long id, DependentsPutDto dto) {
        log.info("Replacing dependent with ID: {}", id);

        Dependents dependents = dependentsRepository.getReferenceById(id);
        dependents.update(dependentsMapper.putDtoToEntity(dto));

        return dependents;
    }

    @Transactional
    public void delete(Long id) {
        log.info("Deleting dependent with ID: {}", id);
        Dependents dependents = dependentsRepository.getReferenceById(id);
        dependents.delete();
    }
}
