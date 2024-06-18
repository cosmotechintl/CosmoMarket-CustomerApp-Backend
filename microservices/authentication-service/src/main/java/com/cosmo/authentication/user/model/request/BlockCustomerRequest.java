package com.cosmo.authentication.user.model.request;

import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockCustomerRequest extends ModelBase {
    private String email;
}
