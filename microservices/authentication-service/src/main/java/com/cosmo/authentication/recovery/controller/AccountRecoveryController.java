package com.cosmo.authentication.recovery.controller;

import com.cosmo.authentication.recovery.mapper.AccountRecoveryMapper;
import com.cosmo.authentication.recovery.model.request.FindMyAccountRequest;
import com.cosmo.authentication.recovery.service.AccountRecoveryService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.ACCOUNT_RECOVERY)
@RequiredArgsConstructor
public class AccountRecoveryController {
    private final AccountRecoveryService accountRecoveryService;

    @PostMapping(ApiConstant.FIND_MY_ACCOUNT)
    public Mono<ApiResponse<?>> findMyAccount(@RequestBody @Valid FindMyAccountRequest findMyAccountRequest) {
        return accountRecoveryService.getCustomerEmailDetails(findMyAccountRequest);
    }
}
