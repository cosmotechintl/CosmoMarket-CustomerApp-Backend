package com.cosmo.customerService.signup.service.impl;

import com.cosmo.authentication.core.constant.EmailSubjectConstant;
import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.mapper.CustomerEmailLogMapper;
import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.OtpUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpServiceImpl implements SignUpService {
    private final CustomerRepository customerRepository;
    private final SignUpMapper signUpMapper;
    private final CustomerEmailLogMapper customerEmailLogMapper;
    private final CustomerEmailLogRepository customerEmailLogRepository;
    private final MailService mailService;
    private final StatusRepository statusRepository;
    private final OtpUtil otpUtil;

    @Override
    @Transactional
    public Mono<ApiResponse> signUp(SignUpModel signUpModel) {
        if (signUpModel.getEmail() == null || signUpModel.getEmail().isEmpty()) {
            return Mono.just(ResponseUtil.getFailureResponse("Email cannot be null or empty"));
        }

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
            log.info("Customer entity created: " + customer);
            CustomerEmailLog customerEmailLog = customerEmailLogMapper.mapToEntity(customer);

            SendEmailRequest sendEmailRequest = new SendEmailRequest();
            sendEmailRequest.setRecipient(customer.getEmail());
            sendEmailRequest.setSubject(EmailSubjectConstant.EMAIL_VERIFICATION);
            sendEmailRequest.setMessage(customerEmailLog.getMessage());
            mailService.sendEmail(sendEmailRequest);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("SignUp Successful. Please verify your email address to login."));
        }catch (Exception ex) {
            return Mono.just(ResponseUtil.getFailureResponse("SignUp Failed" + ex.getMessage()));
        }
    }

    @Override
    public Mono<ApiResponse> verify(OTPVerificationModel otpVerificationModel) {
        if (otpUtil.isValidOTP(otpVerificationModel.getOtp())) {
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
    }
}
