package com.ifba.educampo.mapper;

import com.ifba.educampo.dto.address.AddressPostDto;
import com.ifba.educampo.dto.address.AddressPutDto;
import com.ifba.educampo.dto.address.AddressResponseDto;
import com.ifba.educampo.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponseDto toResponseDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address responseDtoToEntity(AddressResponseDto addressResponseDto);

    AddressPostDto toPostDto(Address address);

    AddressPostDto toPostDto(AddressPutDto addressPutDto);

    @Mapping(target = "id", ignore = true)
    Address postDtoToEntity(AddressPostDto addressPostDto);

    AddressPutDto toPutDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address putDtoToEntity(AddressPutDto addressPutDto);
}
