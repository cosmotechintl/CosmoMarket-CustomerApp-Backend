package com.cosmo.authentication.user.model;

import com.cosmo.common.entity.BloodGroup;
import com.cosmo.common.entity.Gender;
import com.cosmo.common.model.ModelBase;
import com.cosmo.common.model.StatusDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchCustomerResponse extends ModelBase {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Date registeredDate;
    private StatusDto status;
    private BloodGroup bloodGroup;
    private Gender gender;
    private String dateOfBirth;
}
