package com.dietator.diet.projections;

import java.time.LocalDateTime;

public interface ChildBasicInfo {
    long getId();

    String getFirstName();

    LocalDateTime getBirthDate();
}
