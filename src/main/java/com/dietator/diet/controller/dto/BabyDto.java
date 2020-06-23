package com.dietator.diet.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BabyDto {

    private int id;

    private String firstName;

    private LocalDateTime birthDate;
}
