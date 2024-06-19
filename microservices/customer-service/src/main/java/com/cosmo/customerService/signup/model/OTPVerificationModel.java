package com.cosmo.customerService.signup.model;

import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationModel extends ModelBase {

    private String otp;
    private String email;
}
