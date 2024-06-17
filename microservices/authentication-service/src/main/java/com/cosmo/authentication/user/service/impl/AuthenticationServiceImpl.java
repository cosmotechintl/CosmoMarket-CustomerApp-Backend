package com.cosmo.authentication.user.service.impl;

import com.cosmo.authentication.core.service.JwtService;
import com.cosmo.authentication.user.model.AuthenticationRequest;
import com.cosmo.authentication.user.model.AuthenticationResponse;
import com.cosmo.authentication.user.service.AuthenticationService;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public ApiResponse authenticate(AuthenticationRequest request) {
        Optional<Customer> user = customerRepository.findByUsername(request.getUsername());

        if (user.isPresent()) {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            String jwtToken = jwtService.generateToken(user.get());

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwtToken);
            return ResponseUtil.getSuccessfulApiResponse(authenticationResponse, "Sucessfully Logged in.");
        } else {
                return ResponseUtil.getFailureResponse("User not found.");
            }
    }
}
