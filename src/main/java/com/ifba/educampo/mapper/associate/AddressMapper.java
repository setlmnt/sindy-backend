package com.ifba.educampo.mapper.associate;

import com.ifba.educampo.dto.associate.address.AddressPostDto;
import com.ifba.educampo.dto.associate.address.AddressPutDto;
import com.ifba.educampo.dto.associate.address.AddressResponseDto;
import com.ifba.educampo.model.entity.associate.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponseDto toResponseDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address responseDtoToEntity(AddressResponseDto addressResponseDto);

    AddressPostDto toPostDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address postDtoToEntity(AddressPostDto addressPostDto);

    AddressPutDto toPutDto(Address address);

    @Mapping(target = "id", ignore = true)
    Address putDtoToEntity(AddressPutDto addressPutDto);
}
