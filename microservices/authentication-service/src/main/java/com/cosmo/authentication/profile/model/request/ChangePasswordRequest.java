package com.cosmo.authentication.profile.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest extends ModelBase {
    @NotBlank(message = "Old password is required")
    private String oldPassword;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}
