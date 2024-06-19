package com.cosmo.customerService.signup.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpModel {
    private String name;
    private String password;
    private String email;
    private String mobileNumber;
    private String address;
}
