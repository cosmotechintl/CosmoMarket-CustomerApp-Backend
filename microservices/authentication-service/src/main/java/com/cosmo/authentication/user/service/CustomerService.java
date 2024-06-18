package com.cosmo.authentication.user.service;


import com.cosmo.authentication.user.model.FetchCustomerDetail;
import com.cosmo.authentication.user.model.request.BlockCustomerRequest;
import com.cosmo.authentication.user.model.request.DeleteCustomerRequest;
import com.cosmo.authentication.user.model.request.UnblockCustomerRequest;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<ApiResponse<?>> getRegisteredCustomers(SearchParam searchParam);
    Mono<ApiResponse<?>> getCustomerDetails(FetchCustomerDetail fetchCustomerDetail);
    Mono<ApiResponse<?>> deleteCustomer(DeleteCustomerRequest deleteCustomerRequest);
    Mono<ApiResponse<?>> blockCustomer(BlockCustomerRequest blockCustomerRequest);
    Mono<ApiResponse<?>> unblockCustomer(UnblockCustomerRequest unblockCustomerRequest);

}
