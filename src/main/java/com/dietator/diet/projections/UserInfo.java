package com.dietator.diet.projections;

import java.util.Set;

public interface UserInfo {
    long getId();

    String getNickname();

    String getEmail();

    Set<ChildBasicInfo> getChildren();
}
