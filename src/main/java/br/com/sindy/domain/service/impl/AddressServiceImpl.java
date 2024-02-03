package br.com.sindy.domain.service.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.address.AddressPostDto;
import br.com.sindy.domain.dto.address.AddressPutDto;
import br.com.sindy.domain.entity.Address;
import br.com.sindy.domain.mapper.AddressMapper;
import br.com.sindy.domain.repository.AddressRepository;
import br.com.sindy.domain.service.AddressService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@Log
public class AddressServiceImpl implements AddressService {
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
