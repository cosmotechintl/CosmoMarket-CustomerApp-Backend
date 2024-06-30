package com.cosmo.authentication.recovery.service.impl;

import com.cosmo.authentication.core.constant.EmailSubjectConstant;
import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;
import com.cosmo.authentication.recovery.entity.AccountRecoveryEmailLog;
import com.cosmo.authentication.recovery.mapper.AccountRecoveryMapper;
import com.cosmo.authentication.recovery.model.OtpModel;
import com.cosmo.authentication.recovery.model.request.FindMyAccountRequest;
import com.cosmo.authentication.recovery.model.request.ForgotPasswordChangeRequest;
import com.cosmo.authentication.recovery.repo.AccountRecoveryEmailLogRepository;
import com.cosmo.authentication.recovery.service.AccountRecoveryService;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.OtpUtil;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountRecoveryServiceImpl implements AccountRecoveryService {
    private final CustomerRepository customerRepository;
    private final AccountRecoveryMapper accountRecoveryMapper;
    private final AccountRecoveryEmailLogRepository accountRecoveryEmailLogRepository;
    private final MailService mailService;
    private final OtpUtil otpUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Mono<ApiResponse<?>> getCustomerEmailDetails(FindMyAccountRequest findMyAccountRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(findMyAccountRequest.getEmail());
        if(!optionalCustomer.isPresent()){
            return Mono.just(ResponseUtil.getFailureResponse("No account found with the provided email address."));
        }
        else {
            try{
                Customer customer = optionalCustomer.get();
                AccountRecoveryEmailLog accountRecoveryEmailLog = accountRecoveryMapper.sendOtpEmail(customer);
                accountRecoveryEmailLogRepository.save(accountRecoveryEmailLog);

                SendEmailRequest sendEmailRequest = new SendEmailRequest();
                sendEmailRequest.setRecipient(customer.getEmail());
                sendEmailRequest.setSubject(EmailSubjectConstant.EMAIL_VERIFICATION);
                sendEmailRequest.setMessage(accountRecoveryEmailLog.getMessage());
                mailService.sendEmail(sendEmailRequest);
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("A verification mail has been sent to your email."));
            }
            catch(Exception ex){
                return Mono.just(ResponseUtil.getFailureResponse("Account recovery failed" + ex.getMessage()));
            }
        }
    }

    @Override
    public Mono<ApiResponse<?>> verifyOtp(OtpModel otpModel) {
        if (otpUtil.isValidAccountRecoveryOTP(otpModel.getOtp())) {
            AccountRecoveryEmailLog accountRecoveryEmailLog = accountRecoveryEmailLogRepository.findByOtp(otpModel.getOtp());
            accountRecoveryEmailLog.setOtpVerified(true);
            accountRecoveryEmailLogRepository.save(accountRecoveryEmailLog);
            return Mono.just(ResponseUtil.getSuccessfulApiResponse("OTP verification successful."));
        } else {
            return Mono.just(ResponseUtil.getFailureResponse("Password Change Failed. OTP is incorrect or has expired."));
        }
    }

    @Override
    public Mono<ApiResponse<?>> setPassword(ForgotPasswordChangeRequest forgotPasswordChangeRequest) {
        if (!forgotPasswordChangeRequest.getPassword().equals(forgotPasswordChangeRequest.getConfirmPassword())) {
            return Mono.just(ResponseUtil.getFailureResponse("Password and confirm password do not match."));
        }
        Optional<AccountRecoveryEmailLog> optionalLog = accountRecoveryEmailLogRepository.findByEmailAndOtpAndIsOtpVerifiedTrue(
                forgotPasswordChangeRequest.getEmail(), forgotPasswordChangeRequest.getOtp()
        );
        if (optionalLog.isPresent()) {
            Optional<Customer> optionalCustomer = customerRepository.findByEmail(forgotPasswordChangeRequest.getEmail());

            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                customer.setPassword(passwordEncoder.encode(forgotPasswordChangeRequest.getPassword()));
                customerRepository.save(customer);
                return Mono.just(ResponseUtil.getSuccessfulApiResponse("Password changed successfully."));
            } else {
                return Mono.just(ResponseUtil.getFailureResponse("Customer not found."));
            }
        } else {
            return Mono.just(ResponseUtil.getFailureResponse("Fail to change password. The OTP is not verified or incorrect."));
        }
    }
}
