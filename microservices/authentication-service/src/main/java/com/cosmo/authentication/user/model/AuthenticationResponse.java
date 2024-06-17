package com.cosmo.authentication.user.model;

import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse extends ModelBase {

    private String token;

}
