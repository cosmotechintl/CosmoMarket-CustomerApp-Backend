package com.cosmo.authentication.profile.service.impl;

import com.cosmo.authentication.profile.mapper.CustomerProfileMapper;
import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.profile.service.ProfileService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final CustomerProfileMapper customerProfileMapper;
    @Override
    public Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser) {
        var customer= ((Customer)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        CustomerProfileDetailModel customerProfileDetailModel = customerProfileMapper.getUserProfileDetailModel(customer);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse(customerProfileDetailModel,"profile fetched successfully"));
    }
}
