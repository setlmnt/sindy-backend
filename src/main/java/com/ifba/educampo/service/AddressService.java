package com.ifba.educampo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ifba.educampo.domain.Address;
import com.ifba.educampo.exception.BadRequestException;
import com.ifba.educampo.repository.AddressRepository;
import com.ifba.educampo.requests.AddressPutRequestBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService { // Endereco
	private final AddressRepository addressRepository;
	
	public Address findAddress(Long id) {
		return addressRepository.findById(id)
				.orElseThrow(()-> new BadRequestException("Address Not Found"));
	}
	
	public List<Address> listAll() {
        return addressRepository.findAll();
    }
	
	public void delete(long id) {
		addressRepository.delete(findAddress(id));
	}
	
	@Transactional
	public Address save(AddressPutRequestBody addressPostRequestBody) {
		return addressRepository.save(Address.builder()
						.street(addressPostRequestBody.getStreet())
						.city(addressPostRequestBody.getCity())
						.number(addressPostRequestBody.getNumber())
						.complement(addressPostRequestBody.getComplement())
						.neighborhood(addressPostRequestBody.getNeighborhood())
						.zipCode(addressPostRequestBody.getZipCode())
						.build()
				);
	}
	
	public Address replace(AddressPutRequestBody addressPutRequestBody, Long addressId) {
		Address savedAddress = findAddress(addressId);
		return addressRepository.save(Address.builder()
										.id(savedAddress.getId())
										.street(addressPutRequestBody.getStreet())
										.city(addressPutRequestBody.getCity())
										.number(addressPutRequestBody.getNumber())
										.complement(addressPutRequestBody.getComplement())
										.neighborhood(addressPutRequestBody.getNeighborhood())
										.zipCode(addressPutRequestBody.getZipCode())
										.build());
		
	}
	
	public void updateByFields(long id, Map<String, Object> fields) {
		// TODO Auto-generated method stub
		Address savedAddress = findAddress(id);
		
		fields.forEach((key,value)->{
			Field field = ReflectionUtils.findField(Address.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, savedAddress, value);
		});
		addressRepository.save(savedAddress);
	}
	
}
