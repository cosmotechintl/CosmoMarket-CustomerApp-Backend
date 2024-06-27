package com.cosmo.authentication.profile.mapper;

import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.profile.model.request.EditProfileRequest;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.common.repository.BloodGroupRepository;
import com.cosmo.common.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerProfileMapper {

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
}

