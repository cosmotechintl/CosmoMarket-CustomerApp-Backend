package com.cosmo.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum BloodGroupConstant {
    A_POSITIVE("A+","A Positive"),
    A_NEGATIVE("A-","A Negative"),
    B_POSITIVE("B+","B Positive"),
    B_NEGATIVE("B-","B Negative"),
    O_POSITIVE("O+","O Positive"),
    O_NEGATIVE("O-","O Negative"),
    AB_POSITIVE("AB+","AB Positive"),
    AB_NEGATIVE("AB-","AB Negative");
    private final String name;
    private final String description;

}
