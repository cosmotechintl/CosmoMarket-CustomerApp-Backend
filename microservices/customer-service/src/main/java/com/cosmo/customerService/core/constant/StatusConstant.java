package com.cosmo.customerService.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum StatusConstant {

    ACTIVE("ACTIVE", "ACTIVE"),
    PENDING("PENDING", "PENDING");

    private final String name;
    private final String description;

    public static List<String> getUseAbleStatus() {
        return Arrays.asList(StatusConstant.ACTIVE.name());
    }
}
