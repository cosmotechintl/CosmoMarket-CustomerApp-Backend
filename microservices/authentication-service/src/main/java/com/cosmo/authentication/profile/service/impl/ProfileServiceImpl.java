package com.cosmo.authentication.profile.service.impl;

import com.cosmo.authentication.profile.mapper.CustomerProfileMapper;
import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.profile.model.request.ChangeEmailRequest;
import com.cosmo.authentication.profile.model.request.ChangePasswordRequest;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.authentication.profile.service.ProfileService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final CustomerProfileMapper customerProfileMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public Mono<ApiResponse<?>> getProfileDetail(Principal connectedUser) {
        var customer= ((Customer)((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        CustomerProfileDetailModel customerProfileDetailModel = customerProfileMapper.getUserProfileDetailModel(customer);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse(customerProfileDetailModel,"profile fetched successfully"));
    }

    @Override
    public Mono<ApiResponse<?>> changePassword(ChangePasswordRequest changePasswordRequest, Principal connectedUser) {
        var customer = ((Customer) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(),customer.getPassword())){
            return Mono.just(ResponseUtil.getFailureResponse("Old password is incorrect"));
        }

        if(!changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmPassword())){
            return Mono.just(ResponseUtil.getFailureResponse("The passwords do not match"));
        }
        else {
            customer.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
            customer.setPasswordChangeDate(new Date());
            customerRepository.save(customer);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Password changed successfully"));
        }
    }

    @Override
    public Mono<ApiResponse<?>> editProfile(EditProfileRequest editProfileRequest, Principal connectedUser) {
        var customer = ((Customer) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        Customer updatingCustomer = customerProfileMapper.editCustomerProfile(editProfileRequest, customer);
        customerRepository.save(updatingCustomer);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse("Profile updated successfully"));
    }

    @Override
    public Mono<ApiResponse<?>> changeEmail(ChangeEmailRequest changeEmailRequest, Principal connectedUser) {
        var customer = ((Customer) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        if(!passwordEncoder.matches(changeEmailRequest.getPassword(),customer.getPassword())){
            return Mono.just(ResponseUtil.getFailureResponse("The password you have entered is incorrect"));
        }
        else{
            Customer updatingCustomer = customerProfileMapper.changeEmail(changeEmailRequest, customer);
            customerRepository.save(updatingCustomer);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Email changed successfully"));
        }
    }
}
