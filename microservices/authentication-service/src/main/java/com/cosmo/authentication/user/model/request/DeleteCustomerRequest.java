package com.cosmo.authentication.user.model.request;

import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteCustomerRequest extends ModelBase {
    private String email;
}
