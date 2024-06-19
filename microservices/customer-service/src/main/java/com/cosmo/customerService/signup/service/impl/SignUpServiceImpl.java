package com.cosmo.customerService.signup.service.impl;

import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.mapper.CustomerEmailLogMapper;
import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.constant.StatusConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.repository.StatusRepository;
import com.cosmo.common.util.ResponseUtil;
import com.cosmo.customerService.signup.mapper.SignUpMapper;
import com.cosmo.customerService.signup.model.OTPVerificationModel;
import com.cosmo.customerService.signup.model.SignUpModel;
import com.cosmo.customerService.signup.service.SignUpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {
    private final CustomerRepository customerRepository;
    private final SignUpMapper signUpMapper;
    private final CustomerEmailLogMapper customerEmailLogMapper;
    private final CustomerEmailLogRepository customerEmailLogRepository;
    private final MailService mailService;
    private final StatusRepository statusRepository;

    @Override
    @Transactional
    public Mono<ApiResponse> signUp(SignUpModel signUpModel) {
        Optional<Customer> existedEmail = customerRepository.findByEmail(signUpModel.getEmail());
        Optional<Customer> existedNumber = customerRepository.findByMobileNumber(signUpModel.getMobileNumber());

        if (existedEmail.isPresent()) {
            return Mono.just(ResponseUtil.getFailureResponse("This email is already linked to another account. Please use a different email"));
        }

        if (existedNumber.isPresent()) {
            return Mono.just(ResponseUtil.getFailureResponse("The entered mobile number is already linked to another account. Please use a different number"));
        }
        try{
            Customer customer = signUpMapper.mapToEntity(signUpModel);
            CustomerEmailLog customerEmailLog = customerEmailLogMapper.mapToEntity(customer);

            String mailContent = customerEmailLog.getMessage();
            String subject = "User Verification Mail";
            mailService.sendEmail(customer.getEmail(), subject, mailContent);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("SignUp Successful. Please verify your email address to login."));
        }catch (Exception ex) {
            return Mono.just(ResponseUtil.getFailureResponse("SignUp Failed" + ex.getMessage()));
        }
    }

    @Override
    public Mono<ApiResponse> verify(OTPVerificationModel otpVerificationModel) {
        Optional<CustomerEmailLog> customerEmailLogOptional = customerEmailLogRepository.findByEmail(otpVerificationModel.getEmail());

        if (customerEmailLogOptional.isPresent()) {
            CustomerEmailLog customerEmailLog = customerEmailLogOptional.get();

            if (customerEmailLog.getOtp().equals(otpVerificationModel.getOtp()) && !customerEmailLog.isExpired()) {
                Optional<Customer> customerOptional = customerRepository.findByEmail(otpVerificationModel.getEmail());
                if (customerOptional.isPresent()) {
                    Customer customer = customerOptional.get();
                    customer.setStatus(statusRepository.findByName(StatusConstant.ACTIVE.getName()));
                    customerRepository.save(customer);
                }
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("OTP verification successful."));
            } else {
                return Mono.just(ResponseUtil.getFailureResponse("OTP verification failed. OTP is incorrect or has expired."));
            }
        } else {
            return Mono.just(ResponseUtil.getFailureResponse("Please sign up first."));
        }
    }
}
