package com.cosmo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum StatusConstant {

    ACTIVE("ACTIVE", "ACTIVE"),
    PENDING("PENDING", "PENDING"),
    DELETED("DELETED","DELETED" );

    private final String name;
    private final String description;

    public static List<String> getUseAbleStatus() {
        return Arrays.asList(StatusConstant.ACTIVE.name());
    }
}
