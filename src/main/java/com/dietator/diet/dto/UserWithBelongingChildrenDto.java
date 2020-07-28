package com.dietator.diet.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserWithBelongingChildrenDto {
    private Integer id;
    private String nickname;
    private String email;
    private Set<ChildDto> children;
}
