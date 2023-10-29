package com.ifba.educampo.service;

import com.ifba.educampo.dto.AddressDto;
import com.ifba.educampo.exception.NotFoundException;
import com.ifba.educampo.mapper.GenericMapper;
import com.ifba.educampo.model.entity.Address;
import com.ifba.educampo.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService { // Endereco
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);
    private final GenericMapper<AddressDto, Address> modelMapper;
    private final AddressRepository addressRepository;

    public Address findAddress(Long id) {
        LOGGER.info("Finding address with ID: {}", id);
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Address with ID {} not found.", id);
                    return new NotFoundException("Address Not Found");
                });
    }

    public List<Address> listAll() {
        try {
            LOGGER.info("Listing all addresses.");
            return addressRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing addresses.", e);
            throw new RuntimeException("An error occurred while listing addresses.");
        }
    }

    @Transactional
    public void delete(long id) {
        try {
            LOGGER.info("Deleting address with ID: {}", id);
            addressRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing addresses.", e);
            throw new RuntimeException("An error occurred while deleting address.", e);
        }
    }

    @Transactional
    public Address save(AddressDto addressDto) {
        try {
            LOGGER.info("Saving address.");
            return addressRepository.save(modelMapper.mapDtoToModel(addressDto, Address.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing addresses.", e);
            throw new RuntimeException("An error occurred while saving address.", e);
        }
    }

    @Transactional
    public Address replace(AddressDto addressDto, Long addressId) {
        try {
            LOGGER.info("Replacing address with ID: {}", addressId);

            Address savedAddress = findAddress(addressId);
            addressDto.setId(savedAddress.getId());

            return addressRepository.save(modelMapper.mapDtoToModel(addressDto, Address.class));
        } catch (Exception e) {
            LOGGER.error("An error occurred while listing addresses.", e);
            throw new RuntimeException("An error occurred while replacing address.");
        }
    }
}
