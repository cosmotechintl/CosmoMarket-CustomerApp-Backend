package com.cosmo.customerService.signup.service;

import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.customerService.signup.model.OTPVerificationModel;
import com.cosmo.customerService.signup.model.SignUpModel;
import reactor.core.publisher.Mono;

public interface SignUpService {
    Mono<ApiResponse> signUp(SignUpModel signUpModel);

    Mono<ApiResponse> verify(OTPVerificationModel otpVerificationModel);
}
