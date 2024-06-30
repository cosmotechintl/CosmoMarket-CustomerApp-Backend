package com.cosmo.authentication.user.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.authentication.user.model.OTPVerificationModel;
import com.cosmo.authentication.user.model.SignUpModel;
import reactor.core.publisher.Mono;

public interface SignUpService {
    Mono<ApiResponse> signUp(SignUpModel signUpModel);

    Mono<ApiResponse> verify(OTPVerificationModel otpVerificationModel);
}
