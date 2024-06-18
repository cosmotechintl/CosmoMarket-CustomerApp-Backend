package com.cosmo.authentication.user.mapper;


import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerMapper {

    public abstract SearchCustomerResponse entityToResponse(Customer customer);

    public List<SearchCustomerResponse> getCustomerResponses(List<Customer> customers){
        return customers.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}