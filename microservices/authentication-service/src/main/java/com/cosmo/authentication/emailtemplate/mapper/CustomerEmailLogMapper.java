package com.cosmo.authentication.emailtemplate.mapper;

import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.entity.EmailTemplate;
import com.cosmo.authentication.emailtemplate.model.CreateCustomerEmailLog;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import freemarker.template.Template;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.time.Year;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerEmailLogMapper {
    @Autowired
    protected CustomerEmailLogRepository customerEmailLogRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    private freemarker.template.Configuration freeMarkerConfig;
    @Autowired
    protected EmailTemplateRepository emailTemplateRepository;
    public CustomerEmailLog mapToEntity(Customer customer){
        // Calculate expiration time 24 hours from now
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 2);
        Date expirationTime = calendar.getTime();

        // Calculate the difference in minutes between the current time and the expiration time
        long diffInMillies = Math.abs(expirationTime.getTime() - new Date().getTime());
        long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);


        // Generate 6-digit OTP & check whether it already exists in the database
        Random random = new Random();
        String otp;

        do {
            int generateOTP = 100000 + random.nextInt(900000);
            otp = String.valueOf(generateOTP);
        } while (customerEmailLogRepository.existsByOtp(otp));

        // Prepare email content from template
        EmailTemplate emailTemplate = emailTemplateRepository.findEmailTemplateByName("User Verification");
        Map<String, Object> model = new HashMap<>();
        model.put("customerUserName", customer.getName());
        model.put("OTP", otp); // Replace with actual verification link
        model.put("expirationTime", diffInMinutes);
        model.put("currentYear", Year.now().getValue());

        String emailContent;
        try {
            Template template = new Template("emailTemplate", emailTemplate.getTemplate(), freeMarkerConfig);
            emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            // Rollback transaction if there's an error processing the template
            throw new RuntimeException("Error processing email template: "+ e.getMessage());
        }
        CustomerEmailLog customerEmailLog = new CustomerEmailLog();
        customerEmailLog.setEmail(customer.getEmail());
        customerEmailLog.setCustomer(customer);
        customerEmailLog.setMessage(emailContent);
        customerEmailLog.setSent(true);
        customerEmailLog.setOtp(otp);
        customerEmailLog.setExpired(false);
        return  customerEmailLogRepository.save(customerEmailLog);
    }
}
