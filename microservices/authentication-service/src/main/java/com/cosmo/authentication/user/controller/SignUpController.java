package com.cosmo.authentication.user.controller;

import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.authentication.user.service.SignUpService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.authentication.user.model.OTPVerificationModel;
import com.cosmo.authentication.user.model.SignUpModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.SIGNUP)
@RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping()
    public Mono<ApiResponse> signup(@RequestBody @Valid SignUpModel signUpModel){
        return signUpService.signUp(signUpModel);
    }

    @PostMapping(ApiConstant.VERIFY)
    public Mono<ApiResponse> verify(@RequestBody @Valid OTPVerificationModel otpVerificationModel){
        return signUpService.verify(otpVerificationModel);
    }
}
