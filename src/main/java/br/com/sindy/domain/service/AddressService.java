package br.com.sindy.domain.service;

import br.com.sindy.domain.dto.address.AddressPostDto;
import br.com.sindy.domain.dto.address.AddressPutDto;
import br.com.sindy.domain.entity.Address;

public interface AddressService {
    Address save(AddressPostDto dto);

    Address update(Long id, AddressPutDto dto);

    void delete(Long id);
}
