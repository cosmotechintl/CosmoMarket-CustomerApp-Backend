package com.cosmo.authentication.profile.mapper;

import com.cosmo.authentication.profile.model.CustomerProfileDetailModel;
import com.cosmo.authentication.user.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerProfileMapper {
    public abstract CustomerProfileDetailModel getUserProfileDetailModel(Customer customer);
}
