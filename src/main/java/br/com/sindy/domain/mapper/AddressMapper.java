package br.com.sindy.domain.mapper;

import br.com.sindy.domain.dto.address.AddressPostDto;
import br.com.sindy.domain.dto.address.AddressPutDto;
import br.com.sindy.domain.dto.address.AddressResponseDto;
import br.com.sindy.domain.entity.Address;
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
