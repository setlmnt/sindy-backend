package com.ifba.educampo.domain.mapper.associate;

import com.ifba.educampo.domain.dto.associate.workRecord.WorkRecordPostDto;
import com.ifba.educampo.domain.dto.associate.workRecord.WorkRecordPutDto;
import com.ifba.educampo.domain.dto.associate.workRecord.WorkRecordResponseDto;
import com.ifba.educampo.domain.entity.associate.WorkRecord;
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
