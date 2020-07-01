package com.dietator.diet.utils;

import com.dietator.diet.domain.Child;
import com.dietator.diet.dto.ChildDto;

import java.util.Set;
import java.util.stream.Collectors;

public class ChildDtoMapper {

    private ChildDtoMapper() {
    }


    public static Set<ChildDto> mapToChildrenDtos(Set<Child> children) {
        return children
                .stream()
                .map(ChildDtoMapper::mapToChildDto)
                .collect(Collectors.toSet());
    }

    private static ChildDto mapToChildDto(Child child) {
        return ChildDto.builder()
                .id(child.getId())
                .firstName(child.getFirstName())
                .birthDate(child.getBirthDate())
                .build();
    }
}
