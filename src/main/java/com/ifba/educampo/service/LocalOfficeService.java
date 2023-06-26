package com.ifba.educampo.service;

import com.ifba.educampo.domain.Address;
import com.ifba.educampo.domain.Associate;
import com.ifba.educampo.domain.LocalOffice;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.LocalOfficeRepository;
import com.ifba.educampo.requests.LocalOfficePostRequestBody;
import com.ifba.educampo.requests.LocalOfficePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocalOfficeService { // Delegacia (Escritorio Local)
    private final LocalOfficeRepository localOfficeRepository;

    public Page<Associate> listAllAssociates(long localOfficeId, Pageable pageable) {
        return localOfficeRepository.listAllAssociates(localOfficeId, pageable)
                .orElseThrow(()-> new BadRequestException("Associates Not Found"));
    }

    public LocalOffice findLocalOffice(long id) {
        return localOfficeRepository.findById(id).orElseThrow(()-> new BadRequestException("Local Office Not Found"));
    }

    public Page<LocalOffice> listAll(Pageable pageable) {
        return localOfficeRepository.findAll(pageable);
    }

    public ResponseEntity<Void> delete(long id) {
        localOfficeRepository.delete(findLocalOffice(id));
        return ResponseEntity.noContent().build();
    }

    public LocalOffice save(LocalOfficePostRequestBody localOfficePostRequestBody) {
        return localOfficeRepository.save(LocalOffice.builder()
                .name(localOfficePostRequestBody.getName())
                .build());
    }

    public LocalOffice replace(LocalOfficePutRequestBody localOfficePutRequestBody) {
        LocalOffice savedLocalOffice = findLocalOffice(localOfficePutRequestBody.getId());
        return localOfficeRepository.save(LocalOffice.builder()
                .id(savedLocalOffice.getId())
                .name(localOfficePutRequestBody.getName())
                .build());
    }

    public void updateByFields(long id, Map<String, Object> fields) {
        // TODO Auto-generated method stub
        LocalOffice savedLocalOffice = findLocalOffice(id);

        fields.forEach((key,value)->{
            Field field = ReflectionUtils.findField(LocalOffice.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, savedLocalOffice, value);
        });
        localOfficeRepository.save(savedLocalOffice);
    }
}
