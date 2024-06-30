package com.cosmo.authentication.recovery.service;

import com.cosmo.authentication.recovery.model.OtpModel;
import com.cosmo.authentication.recovery.model.request.FindMyAccountRequest;
import com.cosmo.authentication.recovery.model.request.ForgotPasswordChangeRequest;
import com.cosmo.common.model.ApiResponse;
import reactor.core.publisher.Mono;

public interface AccountRecoveryService {
    Mono<ApiResponse<?>> getCustomerEmailDetails(FindMyAccountRequest findMyAccountRequest);
    Mono<ApiResponse<?>> verifyOtp(OtpModel otpModel);
    Mono<ApiResponse<?>> setPassword(ForgotPasswordChangeRequest forgotPasswordChangeRequest);
}
