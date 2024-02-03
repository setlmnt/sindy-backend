package com.ifba.educampo.domain.service;

import com.ifba.educampo.domain.dto.address.AddressPostDto;
import com.ifba.educampo.domain.dto.address.AddressPutDto;
import com.ifba.educampo.domain.entity.Address;

public interface AddressService {
    Address save(AddressPostDto dto);

    Address update(Long id, AddressPutDto dto);

    void delete(Long id);
}
