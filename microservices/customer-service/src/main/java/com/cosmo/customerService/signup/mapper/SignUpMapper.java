package com.cosmo.customerService.signup.mapper;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import com.cosmo.common.constant.StatusConstant;
import com.cosmo.common.repository.StatusRepository;
import com.cosmo.customerService.signup.model.SignUpModel;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SignUpMapper {
    @Autowired
    protected StatusRepository statusRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected CustomerRepository customerRepository;

    @Transactional
    public Customer mapToEntity(SignUpModel signUpModel){
        Customer customer= new Customer();

        customer.setName(signUpModel.getName());
        customer.setMobileNumber(signUpModel.getMobileNumber());
        customer.setAddress(signUpModel.getAddress());
        customer.setEmail(signUpModel.getEmail());
        customer.setUsername(signUpModel.getEmail());
        customer.setActive(false);
        customer.setStatus(statusRepository.findByName(StatusConstant.PENDING.getName()));

        customer.setPassword(passwordEncoder.encode(signUpModel.getPassword()));
        return customerRepository.saveAndFlush(customer);
    }

}
