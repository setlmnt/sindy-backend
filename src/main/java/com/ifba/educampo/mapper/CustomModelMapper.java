package com.ifba.educampo.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper extends ModelMapper {
    public CustomModelMapper() {
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
}
