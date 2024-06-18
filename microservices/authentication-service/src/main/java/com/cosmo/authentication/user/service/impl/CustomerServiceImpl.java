package com.cosmo.authentication.user.service.impl;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.mapper.CustomerMapper;
import com.cosmo.authentication.user.model.CustomerDetailDto;
import com.cosmo.authentication.user.model.FetchCustomerDetail;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import com.cosmo.authentication.user.model.request.BlockCustomerRequest;
import com.cosmo.authentication.user.model.request.DeleteCustomerRequest;
import com.cosmo.authentication.user.model.request.UnblockCustomerRequest;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.user.repo.CustomerSearchRepository;
import com.cosmo.authentication.user.service.CustomerService;
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
        Optional<Customer> customer = customerRepository.findByEmail(deleteCustomerRequest.getEmail());
        if (customer.isEmpty()) {
            return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
        } else {
            Customer customer1 = customer.get();
            if ("BLOCKED".equals(customer1.getStatus().getName()) || "DELETED".equals(customer1.getStatus().getName())) {
                return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
            }
            customer1.setStatus(statusRepository.findByName("DELETED"));
            customer1.setActive(false);
            customerRepository.save(customer1);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer deleted successfully"));
        }
    }

    @Override
    public Mono<ApiResponse<?>> blockCustomer(BlockCustomerRequest blockCustomerRequest) {
        Optional<Customer> customer = customerRepository.findByEmail(blockCustomerRequest.getEmail());
        if (customer.isEmpty()) {
            return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
        } else {
            Customer customer1 = customer.get();
            if ("DELETED".equals(customer1.getStatus().getName()) || "BLOCKED".equals(customer1.getStatus().getName())) {
                return Mono.just(ResponseUtil.getNotFoundResponse("Customer not found"));
            } else {
                customer1.setStatus(statusRepository.findByName("BLOCKED"));
                customer1.setActive(false);
                customerRepository.save(customer1);
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer blocked successfully"));
            }
        }
    }

    @Override
    public Mono<ApiResponse<?>> unblockCustomer(UnblockCustomerRequest unblockCustomerRequest) {
        Optional<Customer> customer = customerRepository.findByEmail(unblockCustomerRequest.getEmail());
        Customer customer1 = customer.get();
        if ("BLOCKED".equals(customer1.getStatus().getName())) {
            customer1.setStatus(statusRepository.findByName("ACTIVE"));
            customer1.setActive(true);
            customerRepository.save(customer1);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Customer unblocked successfully"));
        }
        return Mono.just(ResponseUtil.getFailureResponse("Customer unblock failed"));
    }
}

