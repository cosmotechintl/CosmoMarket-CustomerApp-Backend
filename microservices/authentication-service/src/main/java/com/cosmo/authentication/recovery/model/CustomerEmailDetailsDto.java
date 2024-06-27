package com.cosmo.authentication.recovery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEmailDetailsDto {
    private String email;
    private String firstName;
    private String lastName;
}
