package com.cosmo.authentication.emailtemplate.mapper;

import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.authentication.util.EmailContentUtil;
import com.cosmo.authentication.util.OtpUtil;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerEmailLogMapper {
    @Autowired
    protected CustomerEmailLogRepository customerEmailLogRepository;
    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected EmailTemplateRepository emailTemplateRepository;

    @Autowired
    protected OtpUtil otpUtil;

    @Autowired
    protected EmailContentUtil emailContentUtil;

    @Transactional
    public CustomerEmailLog mapToEntity(Customer customer){

        String otp = otpUtil.generateAndSaveOTP();
        String emailContent = emailContentUtil.prepareEmailContent(customer.getName(), otp);

        CustomerEmailLog customerEmailLog = new CustomerEmailLog();
        customerEmailLog.setEmail(customer.getEmail());
        customerEmailLog.setCustomer(customer);
        customerEmailLog.setMessage(emailContent);
        customerEmailLog.setSent(true);
        customerEmailLog.setOtp(otp);
        return  customerEmailLogRepository.saveAndFlush(customerEmailLog);
    }
}
