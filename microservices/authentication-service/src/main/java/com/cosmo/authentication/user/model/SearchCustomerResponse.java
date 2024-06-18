package com.cosmo.authentication.user.model;

import com.cosmo.common.model.ModelBase;
import com.cosmo.common.model.StatusDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchCustomerResponse extends ModelBase {
    private String name;
    private String mobileNumber;
    private String email;
    private Date registeredDate;
    private StatusDto status;

}
