package com.ifba.educampo.service;

import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.dto.AddressDto;
import com.ifba.educampo.model.entity.Address;
import com.ifba.educampo.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService { // Endereco
    private final GenericMapper<AddressDto, Address> modelMapper;
    private final AddressRepository addressRepository;

    public Address findAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address Not Found"));
    }

    public List<Address> listAll() {
        return addressRepository.findAll();
    }

    public void delete(long id) {
        addressRepository.delete(findAddress(id));
    }

    @Transactional
    public Address save(AddressDto addressDto) {
        return addressRepository.save(modelMapper.mapDtoToModel(addressDto, Address.class));
    }

    public Address replace(AddressDto addressDto, Long addressId) {
        Address savedAddress = findAddress(addressId);
        addressDto.setId(savedAddress.getId());

        return addressRepository.save(modelMapper.mapDtoToModel(addressDto, Address.class));

    }
}
