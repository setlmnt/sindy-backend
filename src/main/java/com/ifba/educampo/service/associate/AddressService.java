package com.ifba.educampo.service.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.associate.address.AddressPostDto;
import com.ifba.educampo.dto.associate.address.AddressPutDto;
import com.ifba.educampo.mapper.associate.AddressMapper;
import com.ifba.educampo.model.entity.associate.Address;
import com.ifba.educampo.repository.associate.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class AddressService { // Endereco
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public Page<Address> listAll(Pageable pageable) {
        log.info("Listing all addresses.");
        return addressRepository.findAll(pageable);
    }

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
