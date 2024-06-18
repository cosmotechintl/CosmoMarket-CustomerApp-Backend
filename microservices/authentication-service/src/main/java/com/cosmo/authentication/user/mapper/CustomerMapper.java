package com.cosmo.authentication.user.mapper;


import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.model.CustomerDetailDto;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import com.cosmo.authentication.user.model.request.UpdateCustomerRequest;
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
    public abstract CustomerDetailDto getCustomerDetails(Customer customer);

    public Customer updateCustomer(UpdateCustomerRequest request, Customer customer){
        customer.setName(request.getName());
        customer.setUsername(request.getUsername());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setAddress(request.getAddress());
        customer.setProfilePictureName(request.getProfilePictureName());
        return  customer;
    }

}