package com.cosmo.authentication.user.service;

import com.cosmo.authentication.user.model.AuthenticationRequest;
import com.cosmo.authentication.user.model.AuthenticationResponse;
import com.cosmo.common.model.ApiResponse;

public interface AuthenticationService {
    ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request);
}
