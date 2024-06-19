package com.cosmo.authentication.user.service.impl;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.mapper.CustomerMapper;
import com.cosmo.authentication.user.model.CustomerDetailDto;
import com.cosmo.authentication.user.model.FetchCustomerDetail;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import com.cosmo.authentication.user.model.request.BlockCustomerRequest;
import com.cosmo.authentication.user.model.request.DeleteCustomerRequest;
import com.cosmo.authentication.user.model.request.UnblockCustomerRequest;
import com.cosmo.authentication.user.model.request.UpdateCustomerRequest;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.user.repo.CustomerSearchRepository;
import com.cosmo.authentication.user.service.CustomerService;
import com.cosmo.common.constant.StatusConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.PageableResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.model.SearchResponseWithMapperBuilder;
import com.cosmo.common.repository.StatusRepository;
import com.cosmo.common.service.SearchResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearchRepository customerSearchRepository;
    private final CustomerMapper customerMapper;
    private final SearchResponse searchResponse;
    private final StatusRepository statusRepository;

    @Override
    public Mono<ApiResponse<?>> getRegisteredCustomers(SearchParam searchParam) {
        SearchResponseWithMapperBuilder<Customer, SearchCustomerResponse> responseBuilder =
                SearchResponseWithMapperBuilder.<Customer, SearchCustomerResponse>builder()
                        .count(customerSearchRepository::count)
                        .searchData(customerSearchRepository::getAll)
                        .mapperFunction(this.customerMapper::getCustomerResponses)
                        .searchParam(searchParam)
                        .build();
        PageableResponse<SearchCustomerResponse> response = searchResponse.getSearchResponse(responseBuilder);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse(response, "Registered customers fetched successfully"));
    }

    @Override
    public Mono<ApiResponse<?>> getCustomerDetails(FetchCustomerDetail fetchCustomerDetail) {
        Optional<Customer> customer = customerRepository.findByEmail(fetchCustomerDetail.getEmail());
        if (customer.isEmpty()) {
            return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
        } else {
            CustomerDetailDto customerDetailDto = customerMapper.getCustomerDetails(customer.get());
            return Mono.just(ResponseUtil.getSuccessfulApiResponse(customerDetailDto, "Customer details fetched successfully"));
        }
    }

    @Override
    public Mono<ApiResponse<?>> deleteCustomer(DeleteCustomerRequest deleteCustomerRequest) {
        Optional<Customer> checkCustomer = customerRepository.findByEmail(deleteCustomerRequest.getEmail());
        if (checkCustomer.isEmpty()) {
            return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
        } else {
            Customer customer = checkCustomer.get();
            if (StatusConstant.BLOCKED.getName().equals(customer.getStatus().getName()) || StatusConstant.DELETED.getName().equals(customer.getStatus().getName())) {
                return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
            }
            customer.setStatus(statusRepository.findByName(StatusConstant.DELETED.getName()));
            customer.setActive(false);
            customerRepository.save(customer);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer deleted successfully"));
        }
    }

    @Override
    public Mono<ApiResponse<?>> blockCustomer(BlockCustomerRequest blockCustomerRequest) {
        Optional<Customer> checkCustomer = customerRepository.findByEmail(blockCustomerRequest.getEmail());
        if (checkCustomer.isEmpty()) {
            return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
        } else {
            Customer customer = checkCustomer.get();
            if (StatusConstant.DELETED.getName().equals(customer.getStatus().getName()) || StatusConstant.BLOCKED.getName().equals(customer.getStatus().getName())) {
                return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
            } else {
                customer.setStatus(statusRepository.findByName(StatusConstant.BLOCKED.getName()));
                customer.setActive(false);
                customerRepository.save(customer);
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer blocked successfully"));
            }
        }
    }

    @Override
    public Mono<ApiResponse<?>> unblockCustomer(UnblockCustomerRequest unblockCustomerRequest) {
        Optional<Customer> checkCustomer = customerRepository.findByEmail(unblockCustomerRequest.getEmail());
        if(checkCustomer.isPresent()){
            Customer customer = checkCustomer.get();
            if (StatusConstant.BLOCKED.getName().equals(customer.getStatus().getName())) {
                customer.setStatus(statusRepository.findByName(StatusConstant.ACTIVE.getName()));
                customer.setActive(true);
                customerRepository.save(customer);
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer unblocked successfully"));
        }
        }
        return Mono.just(ResponseUtil.getFailureResponse("Customer unblock failed"));
    }

    @Override
    public Mono<ApiResponse<?>> updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        Optional<Customer> existedNumber = customerRepository.findByMobileNumber(updateCustomerRequest.getMobileNumber());
        if (existedNumber.isPresent() && !existedNumber.get().getEmail().equals(updateCustomerRequest.getEmail())) {
            return Mono.just(ResponseUtil.getFailureResponse("The mobile number is linked to another account."));
        }
        Optional<Customer> checkCustomer = customerRepository.findByEmail(updateCustomerRequest.getEmail());
        if (checkCustomer.isPresent()){
            Customer customer = checkCustomer.get();
            if (StatusConstant.BLOCKED.getName().equals(customer.getStatus().getName()) || StatusConstant.DELETED.getName().equals(customer.getStatus().getName())) {
                return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
            } else {
                Customer updatedCustomer = customerMapper.updateCustomer(updateCustomerRequest, customer);
                customerRepository.save(updatedCustomer);
        }
        }
        return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer updated successfully"));
    }
}

