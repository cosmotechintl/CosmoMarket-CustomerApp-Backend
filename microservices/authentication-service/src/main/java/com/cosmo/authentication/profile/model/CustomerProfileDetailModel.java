package com.cosmo.authentication.profile.model;

import com.cosmo.common.entity.Gender;
import com.cosmo.common.model.GenderDto;
import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CustomerProfileDetailModel extends ModelBase {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String address;
    private GenderDto gender;
    private String profilePictureName;
    private String dateOfBirth;
}
