package com.cosmo.authentication.emailtemplate.model;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerEmailLog extends ModelBase {

    @Email(message = "This email is invalid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    private Customer customer;

    private boolean isSent;

    private String otp;
}
