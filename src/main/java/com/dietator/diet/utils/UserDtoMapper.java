package com.dietator.diet.utils;


import com.dietator.diet.domain.User;
import com.dietator.diet.dto.UserDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {

    private UserDtoMapper() {
    }

    public static List<UserDto> mapToUserDtos(Set<User> users) {
        return users
                .stream()
                .map(UserDtoMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    private static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .children(ChildDtoMapper.mapToChildrenDtos(user.getChildren()))
                .build();
    }

}
