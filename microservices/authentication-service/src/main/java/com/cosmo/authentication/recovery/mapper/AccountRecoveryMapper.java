package com.cosmo.authentication.recovery.mapper;

import com.cosmo.authentication.core.constant.EmailTemplateConstant;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import com.cosmo.authentication.recovery.entity.AccountRecoveryEmailLog;
import com.cosmo.authentication.recovery.model.CustomerEmailDetailsDto;
import com.cosmo.authentication.recovery.repo.AccountRecoveryEmailLogRepository;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.EmailContentUtil;
import com.cosmo.authentication.util.OtpUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AccountRecoveryMapper {
    @Autowired
    protected AccountRecoveryEmailLogRepository accountRecoveryEmailLogRepository;
    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected EmailTemplateRepository emailTemplateRepository;

    @Autowired
    protected OtpUtil otpUtil;

    @Autowired
    protected EmailContentUtil emailContentUtil;

    public abstract CustomerEmailDetailsDto getCustomerEmailDetails(Customer customer);

    public AccountRecoveryEmailLog sendOtpEmail(Customer customer){
        String otp = otpUtil.generateAccountRecoveryOTP();
        String emailContent = emailContentUtil.prepareEmailContent(customer.getFirstName(), otp, EmailTemplateConstant.ACCOUNT_RECOVERY);

        AccountRecoveryEmailLog accountRecoveryEmailLog = new AccountRecoveryEmailLog();
        accountRecoveryEmailLog.setEmail(customer.getEmail());
        accountRecoveryEmailLog.setCustomer(customer);
        accountRecoveryEmailLog.setMessage(emailContent);
        accountRecoveryEmailLog.setSent(true);
        accountRecoveryEmailLog.setOtp(otp);
        accountRecoveryEmailLog.setTimestamp(LocalDateTime.now());
        return accountRecoveryEmailLog;
    }

}
