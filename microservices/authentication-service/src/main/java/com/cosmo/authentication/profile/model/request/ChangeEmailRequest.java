package com.cosmo.authentication.profile.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailRequest extends ModelBase {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
