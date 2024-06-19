package com.cosmo.authentication.user.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCustomerRequest extends ModelBase {
    @NotBlank(message = "email is required.")
    private String email;
}
