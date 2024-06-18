package com.cosmo.authentication.user.service;


import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<ApiResponse<?>> getRegisteredCustomers(SearchParam searchParam);

}
