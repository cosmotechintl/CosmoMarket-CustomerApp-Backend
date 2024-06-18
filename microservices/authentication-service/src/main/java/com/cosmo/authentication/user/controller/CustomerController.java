package com.cosmo.authentication.user.controller;

import com.cosmo.authentication.user.model.FetchCustomerDetail;
import com.cosmo.authentication.user.model.request.BlockCustomerRequest;
import com.cosmo.authentication.user.model.request.DeleteCustomerRequest;
import com.cosmo.authentication.user.model.request.UnblockCustomerRequest;
import com.cosmo.authentication.user.service.CustomerService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(ApiConstant.GET)
    public Mono<ApiResponse<?>> getRegisteredCustomers(@RequestBody SearchParam searchParam) {
        return customerService.getRegisteredCustomers(searchParam);
    }

    @PostMapping(ApiConstant.GET + ApiConstant.SLASH + ApiConstant.DETAIL)
    public Mono<ApiResponse<?>> getCustomerDetails(@RequestBody @Valid FetchCustomerDetail fetchCustomerDetail) {
        return customerService.getCustomerDetails(fetchCustomerDetail);
    }

    @PostMapping(ApiConstant.DELETE)
    public Mono<ApiResponse<?>> deleteCustomer(@RequestBody DeleteCustomerRequest request) {
        return customerService.deleteCustomer(request);
    }

    @PostMapping(ApiConstant.BLOCK)
    public Mono<ApiResponse<?>> blockCustomer(@RequestBody BlockCustomerRequest request) {
        return customerService.blockCustomer(request);
    }

    @PostMapping(ApiConstant.UNBLOCK)
    public Mono<ApiResponse<?>> unblock(@RequestBody UnblockCustomerRequest request) {
        return customerService.unblockCustomer(request);
    }
}
