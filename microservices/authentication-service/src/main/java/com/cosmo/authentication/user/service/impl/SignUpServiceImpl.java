package com.cosmo.authentication.user.service.impl;

import com.cosmo.authentication.core.constant.EmailSubjectConstant;
import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.mapper.CustomerEmailLogMapper;
import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.user.mapper.CustomerMapper;
import com.cosmo.authentication.user.service.SignUpService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.OtpUtil;
import com.cosmo.common.constant.StatusConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.repository.StatusRepository;
import com.cosmo.common.util.ResponseUtil;
import com.cosmo.authentication.user.model.OTPVerificationModel;
import com.cosmo.authentication.user.model.SignUpModel;
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
    private final CustomerEmailLogMapper customerEmailLogMapper;
    private final CustomerEmailLogRepository customerEmailLogRepository;
    private final MailService mailService;
    private final StatusRepository statusRepository;
    private final OtpUtil otpUtil;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public Mono<ApiResponse> signUp(SignUpModel signUpModel) {
        Optional<Customer> existingCustomerWithEmail = customerRepository.findByEmail(signUpModel.getEmail());
        Optional<Customer> existingCustomerWithPhone = customerRepository.findByMobileNumber(signUpModel.getMobileNumber());

        if (existingCustomerWithEmail.isPresent()) {
            return Mono.just(ResponseUtil.getFailureResponse("The user with email already exist."));
        }
        if (existingCustomerWithPhone.isPresent()) {
            return Mono.just(ResponseUtil.getFailureResponse("The user with phone number already exist."));
        }
        try {
            Customer customer = customerMapper.signUpMapToEntity(signUpModel);
            log.info("Customer entity created: " + customer);
            customerRepository.save(customer);

            CustomerEmailLog customerEmailLog = customerEmailLogMapper.mapToEntity(customer);
            customerEmailLogRepository.save(customerEmailLog);

            SendEmailRequest sendEmailRequest = new SendEmailRequest();
            sendEmailRequest.setRecipient(customer.getEmail());
            sendEmailRequest.setSubject(EmailSubjectConstant.EMAIL_VERIFICATION);
            sendEmailRequest.setMessage(customerEmailLog.getMessage());
            mailService.sendEmail(sendEmailRequest);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("SignUp Successful. Please verify your email address to continue."));
        } catch (Exception ex) {
            return Mono.just(ResponseUtil.getFailureResponse("SignUp Failed" + ex.getMessage()));
        }
    }

    @Override
    public Mono<ApiResponse> verify(OTPVerificationModel otpVerificationModel) {
        if (otpUtil.isValidRegistrationOTP(otpVerificationModel.getOtp())) {
            Optional<Customer> customerOptional = customerRepository.findByEmail(otpVerificationModel.getEmail());
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                customer.setStatus(statusRepository.findByName(StatusConstant.ACTIVE.getName()));
                customer.setActive(true);
                customerRepository.save(customer);
            }
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("OTP verification successful."));
        } else {
            return Mono.just(ResponseUtil.getFailureResponse("OTP verification failed. OTP is incorrect or has expired."));
        }
    }

}
