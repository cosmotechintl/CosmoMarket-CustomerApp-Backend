package com.cosmo.authentication.user.service.impl;


import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.mapper.CustomerMapper;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.user.repo.CustomerSearchRepository;
import com.cosmo.authentication.user.service.CustomerService;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.PageableResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.model.SearchResponseWithMapperBuilder;
import com.cosmo.common.service.SearchResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerSearchRepository customerSearchRepository;
    private final CustomerMapper customerMapper;
    private final SearchResponse searchResponse;

    @Override
    public Mono<ApiResponse<?>> getRegisteredCustomers(SearchParam searchParam) {
        SearchResponseWithMapperBuilder<Customer, SearchCustomerResponse> responseBuilder =
                SearchResponseWithMapperBuilder.<Customer,SearchCustomerResponse>builder()
                        .count(customerSearchRepository::count)
                        .searchData(customerSearchRepository::getAll)
                        .mapperFunction(this.customerMapper::getCustomerResponses)
                        .searchParam(searchParam)
                        .build();
        PageableResponse<SearchCustomerResponse> response = searchResponse.getSearchResponse(responseBuilder);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse(response,"Registered customers fetched successfully"));
    }

}
