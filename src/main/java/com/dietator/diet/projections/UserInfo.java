package com.dietator.diet.projections;

import java.util.Set;

public interface UserInfo {
    int getId();

    String getNickname();

    String getEmail();

    Set<ChildBasicInfo> getChildren();
}
