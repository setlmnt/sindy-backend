package com.ifba.educampo.service;

import com.ifba.educampo.dto.address.AddressPostDto;
import com.ifba.educampo.dto.address.AddressPutDto;
import com.ifba.educampo.entity.Address;

public interface AddressService {
    Address save(AddressPostDto dto);

    Address update(Long id, AddressPutDto dto);

    void delete(Long id);
}
