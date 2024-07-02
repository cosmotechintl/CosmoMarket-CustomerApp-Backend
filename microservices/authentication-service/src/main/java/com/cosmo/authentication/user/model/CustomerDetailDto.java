package com.cosmo.authentication.user.model;

import com.cosmo.common.model.BloodGroupDto;
import com.cosmo.common.model.GenderDto;
import com.cosmo.common.model.ModelBase;
import com.cosmo.common.model.StatusDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDetailDto extends ModelBase {
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String mobileNumber;
    private String email;
    private String profilePictureName;
    private StatusDto status;
    private BloodGroupDto bloodGroup;
    private GenderDto gender;
    private String dateOfBirth;
}
