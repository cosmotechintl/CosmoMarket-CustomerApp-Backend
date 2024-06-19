package com.cosmo.authentication.user.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerRequest extends ModelBase {
    @NotBlank(message = "email is required.")
    private String email;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotNull(message = "Mobile number cannot be blank")
    @Size(min = 10,max = 10,message = "Invalid mobile number")
    private String mobileNumber;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    private String profilePictureName;
}
