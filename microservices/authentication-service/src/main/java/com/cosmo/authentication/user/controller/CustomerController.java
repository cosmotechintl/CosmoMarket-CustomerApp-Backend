package com.cosmo.authentication.user.controller;

import com.cosmo.authentication.user.service.CustomerService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(ApiConstant.GET)
    public Mono<ApiResponse<?>> getRegisteredCustomers(@RequestBody SearchParam searchParam){
        return customerService.getRegisteredCustomers(searchParam);
    }
}
