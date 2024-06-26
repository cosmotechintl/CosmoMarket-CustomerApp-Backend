package com.cosmo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderConstant {
    MALE("Male","male"),
    FEMALE("Female","Female"),
    OTHERS("Others","Others"),
    PREFER_NOT_TO_SAY("Prefer not to say","Prefer not to say");

    private final String name;
    private final String description;
}
