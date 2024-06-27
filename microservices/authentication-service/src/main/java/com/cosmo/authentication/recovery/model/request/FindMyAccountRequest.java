package com.cosmo.authentication.recovery.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMyAccountRequest extends ModelBase {
    @NotBlank(message = "Please enter the email address associated with your account.")
    @Email(message = "Please enter a valid email address.")
    private String email;
    private String firstName;
    private String lastName;
}
