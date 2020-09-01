package com.dietator.diet.projections;

import java.time.LocalDate;

public interface ChildBasicInfo {
    long getId();

    String getFirstName();

    LocalDate getBirthDate();
}
