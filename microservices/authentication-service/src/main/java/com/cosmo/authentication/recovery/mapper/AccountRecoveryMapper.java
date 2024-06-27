package com.cosmo.authentication.recovery.mapper;

import com.cosmo.authentication.recovery.model.CustomerEmailDetailsDto;
import com.cosmo.authentication.user.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AccountRecoveryMapper {
    public abstract CustomerEmailDetailsDto getCustomerEmailDetails(Customer customer);
}
