package com.cosmo.authentication.recovery.service.impl;

import com.cosmo.authentication.recovery.mapper.AccountRecoveryMapper;
import com.cosmo.authentication.recovery.model.CustomerEmailDetailsDto;
import com.cosmo.authentication.recovery.model.request.FindMyAccountRequest;
import com.cosmo.authentication.recovery.service.AccountRecoveryService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountRecoveryServiceImpl implements AccountRecoveryService {
    private final CustomerRepository customerRepository;
    private final AccountRecoveryMapper accountRecoveryMapper;

    @Override
    public Mono<ApiResponse<?>> getCustomerEmailDetails(FindMyAccountRequest findMyAccountRequest) {
        Optional<Customer> customer = customerRepository.findByEmail(findMyAccountRequest.getEmail());
        if(!customer.isPresent()){
            return Mono.just(ResponseUtil.getFailureResponse("No account found with the provided email address."));
        }
        else {
            CustomerEmailDetailsDto customerEmailDetailsDto = accountRecoveryMapper.getCustomerEmailDetails(customer.get());
            return Mono.just(ResponseUtil.getSuccessfulApiResponse(customerEmailDetailsDto,"A verification mail has been sent to your email."));
        }
    }
}
