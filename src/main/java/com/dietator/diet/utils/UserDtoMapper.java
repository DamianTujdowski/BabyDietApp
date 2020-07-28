package com.dietator.diet.utils;


import com.dietator.diet.domain.User;
import com.dietator.diet.dto.UserBasicInfoDto;
import com.dietator.diet.dto.UserWithBelongingChildrenDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {

    private UserDtoMapper() {
    }

    public static List<UserWithBelongingChildrenDto> mapToUserWithBelongingChildrenDtos(Set<User> users) {
        return users.stream()
                .map(UserDtoMapper::mapToUserWithBelongingChildrenDto)
                .collect(Collectors.toList());
    }

    private static UserWithBelongingChildrenDto mapToUserWithBelongingChildrenDto(User user) {
        return UserWithBelongingChildrenDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .children(ChildDtoMapper.mapToChildrenDtos(user.getChildren()))
                .build();
    }

    public static List<UserBasicInfoDto> mapToUserBasicInfoDtos(Set<User> users) {
        return users.stream()
                .map(UserDtoMapper::mapToUserBasicInfoDto)
                .collect(Collectors.toList());
    }

    private static UserBasicInfoDto mapToUserBasicInfoDto(User user) {
        return UserBasicInfoDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public static UserWithBelongingChildrenDto mapToSingleUserDto(User userById) {
        return mapToUserWithBelongingChildrenDto(userById);
    }
}
