package com.cosmo.authentication.profile.mapper;

import com.cosmo.authentication.core.constant.EmailTemplateConstant;
import com.cosmo.authentication.profile.entity.ChangeEmailLog;
import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.profile.model.request.ChangeEmailRequest;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.util.EmailContentUtil;
import com.cosmo.authentication.util.OtpUtil;
import com.cosmo.common.repository.BloodGroupRepository;
import com.cosmo.common.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerProfileMapper {

    @Autowired
    protected OtpUtil otpUtil;
    @Autowired
    protected EmailContentUtil emailContentUtil;


    public abstract CustomerProfileDetailModel getUserProfileDetailModel(Customer customer);

    public Customer editCustomerProfile(EditProfileRequest editProfileRequest, Customer customer){
        customer.setFirstName(editProfileRequest.getFirstName());
        customer.setLastName(editProfileRequest.getLastName());
        customer.setMobileNumber(editProfileRequest.getMobileNumber());
        customer.setAddress(editProfileRequest.getAddress());
        customer.setProfilePictureName(editProfileRequest.getProfilePictureName());
        customer.setUsername(editProfileRequest.getUsername());
        customer.setDateOfBirth(editProfileRequest.getDateOfBirth());
        return customer;
    }
    public ChangeEmailLog sendOtpEmail(Customer customer){
        String otp = otpUtil.generateEmailChangeOTP();
        String emailContent = emailContentUtil.prepareEmailContent(customer.getFirstName(),otp, EmailTemplateConstant.CHANGE_EMAIL);
        ChangeEmailLog changeEmailLog = new ChangeEmailLog();
        changeEmailLog.setEmail(customer.getEmail());
        changeEmailLog.setCustomer(customer);
        changeEmailLog.setMessage(emailContent);
        changeEmailLog.setSent(true);
        changeEmailLog.setOtp(otp);
        changeEmailLog.setTimestamp(LocalDateTime.now());
        return changeEmailLog;
    }
}

