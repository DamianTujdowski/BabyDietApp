package com.dietator.diet.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChildDto {
    private int id;
    private String firstName;
    private LocalDateTime birthDate;
}
