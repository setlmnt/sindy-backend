package br.com.sindy.domain.mapper.associate;

import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPostDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordPutDto;
import br.com.sindy.domain.dto.associate.workRecord.WorkRecordResponseDto;
import br.com.sindy.domain.entity.associate.WorkRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkRecordMapper {
    WorkRecordResponseDto toResponseDto(WorkRecord workRecord);

    @Mapping(target = "id", ignore = true)
    WorkRecord responseDtoToEntity(WorkRecordResponseDto workRecordResponseDto);

    WorkRecordPostDto toPostDto(WorkRecord workRecord);

    @Mapping(target = "id", ignore = true)
    WorkRecord postDtoToEntity(WorkRecordPostDto workRecordPostDto);

    WorkRecordPutDto toPutDto(WorkRecord workRecord);

    @Mapping(target = "id", ignore = true)
    WorkRecord putDtoToEntity(WorkRecordPutDto associatePutDto);
}
