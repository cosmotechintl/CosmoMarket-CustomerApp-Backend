package com.cosmo.authentication.profile.service.impl;

import com.cosmo.authentication.core.constant.EmailSubjectConstant;
import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;
import com.cosmo.authentication.profile.entity.ChangeEmailLog;
import com.cosmo.authentication.profile.mapper.CustomerProfileMapper;
import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.profile.model.request.ChangeEmailRequest;
import com.cosmo.authentication.profile.model.request.ChangePasswordRequest;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.authentication.profile.repo.ChangeEmailLogRepository;
import com.cosmo.authentication.profile.service.ProfileService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.OtpUtil;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.OtpModel;
import com.cosmo.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final CustomerProfileMapper customerProfileMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final ChangeEmailLogRepository changeEmailLogRepository;
    private final MailService mailService;
    private final OtpUtil otpUtil;

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
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(changeEmailRequest.getEmail());
        if(optionalCustomer.isPresent()){
            return Mono.just(ResponseUtil.getFailureResponse("The user with provided email already exists"));
        }
        else {
            if (!passwordEncoder.matches(changeEmailRequest.getPassword(), customer.getPassword())) {
                return Mono.just(ResponseUtil.getFailureResponse("The password you have entered is incorrect"));
            } else {
                try{
                    ChangeEmailLog changeEmailLog = customerProfileMapper.sendOtpEmail(customer);
                    changeEmailLogRepository.save(changeEmailLog);

                    SendEmailRequest sendEmailRequest = new SendEmailRequest();
                    sendEmailRequest.setRecipient(changeEmailRequest.getEmail());
                    sendEmailRequest.setSubject(EmailSubjectConstant.CHANGE_EMAIL_REQUEST);
                    sendEmailRequest.setMessage(changeEmailLog.getMessage());
                    mailService.sendEmail(sendEmailRequest);
                    return Mono.just(ResponseUtil.getSuccessfulApiResponse("A verification mail has been send to provided email"));
                }
                catch(Exception ex){
                    return Mono.just(ResponseUtil.getFailureResponse("Email Sending Failed" + ex.getMessage()));
                }
            }
        }
    }

    @Override
    public Mono<ApiResponse<?>> verifyOtpAndSetEmail(OtpModel otpModel, Principal connectedUser) {
        var customer = ((Customer) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
        if (otpUtil.isValidChangeEmailOtp(otpModel.getOtp())){
            ChangeEmailLog changeEmailLog = changeEmailLogRepository.findByOtp(otpModel.getOtp());
            changeEmailLog.setOtpVerified(true);
            changeEmailLogRepository.save(changeEmailLog);

            customer.setEmail(otpModel.getEmail());
            customer.setUsername(otpModel.getEmail());
            customerRepository.save(customer);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("Email changed successfully."));
        }
        else{
            return Mono.just(ResponseUtil.getFailureResponse("Fail to change email. The OTP is invalid."));
        }
    }
}
