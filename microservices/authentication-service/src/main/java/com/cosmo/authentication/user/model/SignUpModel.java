package com.cosmo.authentication.user.model;

import com.cosmo.common.entity.BloodGroup;
import com.cosmo.common.entity.Gender;
import com.cosmo.common.model.BloodGroupDto;
import com.cosmo.common.model.GenderDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpModel {
    @NotBlank(message = "First name is required.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    private String lastName;
    @NotBlank(message = "Mobile number is required.")
    @Size(min = 10, max = 10, message = "Invalid mobile number format")
    private String mobileNumber;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required.")
    private String email;
    @NotBlank(message = "Password is required.")
    private String password;
    @NotNull(message = "Date of birth is required.")
    private LocalDate dob;
    @NotNull(message="Gender is required")
    private GenderDto gender;
    @NotNull(message="Blood group is required")
    private BloodGroupDto bloodGroup;
}
