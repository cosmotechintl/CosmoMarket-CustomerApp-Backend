package com.cosmo.authentication.recovery.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordChangeRequest extends ModelBase {
    @Email(message = "Invalid email format")
    @NotBlank(message="Email is required")
    private String email;
    @NotBlank(message="OTP is required")
    private String otp;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
