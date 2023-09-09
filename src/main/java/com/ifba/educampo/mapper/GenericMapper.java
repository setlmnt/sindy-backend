package com.ifba.educampo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericMapper<S, T> {
    @Autowired
    private CustomModelMapper customModelMapper;

    public T mapDtoToModel(S dto, Class<T> targetClass) {
        return customModelMapper.map(dto, targetClass);
    }

    public S mapModelToDto(T model, Class<S> targetClass) {
        return customModelMapper.map(model, targetClass);
    }

    public List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> customModelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
