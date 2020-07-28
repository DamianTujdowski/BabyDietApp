package com.dietator.diet.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserBasicInfoDto {
    private Integer id;
    private String nickname;
    private String email;
}
