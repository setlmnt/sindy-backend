package com.ifba.educampo.service;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.address.AddressPostDto;
import com.ifba.educampo.dto.address.AddressPutDto;
import com.ifba.educampo.entity.Address;
import com.ifba.educampo.mapper.AddressMapper;
import com.ifba.educampo.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public Address save(AddressPostDto dto) {
        log.info("Saving address.");
        return addressRepository.save(addressMapper.postDtoToEntity(dto));
    }

    public Address update(Long id, AddressPutDto dto) {
        log.info("Replacing address with ID: {}", id);

        Address address = addressRepository.getReferenceById(id);
        address.update(addressMapper.putDtoToEntity(dto));

        return address;
    }

    public void delete(Long id) {
        log.info("Deleting address with ID: {}", id);
        Address address = addressRepository.getReferenceById(id);
        address.delete();
    }
}
