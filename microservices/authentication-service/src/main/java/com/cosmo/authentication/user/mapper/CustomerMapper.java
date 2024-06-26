package com.cosmo.authentication.user.mapper;


import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.model.CustomerDetailDto;
import com.cosmo.authentication.user.model.SearchCustomerResponse;
import com.cosmo.authentication.user.model.SignUpModel;
import com.cosmo.authentication.user.model.request.UpdateCustomerRequest;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.constant.StatusConstant;
import com.cosmo.common.repository.BloodGroupRepository;
import com.cosmo.common.repository.GenderRepository;
import com.cosmo.common.repository.StatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CustomerMapper {

    @Autowired
    protected GenderRepository genderRepository;
    @Autowired
    protected BloodGroupRepository bloodGroupRepository;
    @Autowired
    protected StatusRepository statusRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected CustomerRepository customerRepository;

    public Customer signUpMapToEntity(SignUpModel signUpModel){
        Customer customer = new Customer();
        customer.setFirstName(signUpModel.getFirstName());
        customer.setLastName(signUpModel.getLastName());
        customer.setMobileNumber(signUpModel.getMobileNumber());
        customer.setEmail(signUpModel.getEmail());
        customer.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        customer.setDateOfBirth(signUpModel.getDob());
        customer.setGender(genderRepository.findByName(signUpModel.getGender().getName()));
        customer.setBloodGroup(bloodGroupRepository.findByName(signUpModel.getBloodGroup().getName()));
        customer.setStatus(statusRepository.findByName(StatusConstant.PENDING.getName()));
        return customer;
    }

    public abstract SearchCustomerResponse entityToResponse(Customer customer);

    public List<SearchCustomerResponse> getCustomerResponses(List<Customer> customers){
        return customers.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
    public abstract CustomerDetailDto getCustomerDetails(Customer customer);

    public Customer updateCustomer(UpdateCustomerRequest request, Customer customer){
        customer.setFirstName(request.getName());
        customer.setUsername(request.getUsername());
        customer.setMobileNumber(request.getMobileNumber());
        customer.setAddress(request.getAddress());
        customer.setProfilePictureName(request.getProfilePictureName());
        return  customer;
    }

}