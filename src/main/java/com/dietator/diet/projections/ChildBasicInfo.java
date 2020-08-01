package com.dietator.diet.projections;

import java.time.LocalDateTime;

public interface ChildBasicInfo {
    int getId();

    String getFirstName();

    LocalDateTime getBirthDate();
}
