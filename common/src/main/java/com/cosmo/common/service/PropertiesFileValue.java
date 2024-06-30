package com.cosmo.common.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertiesFileValue {

    @Value("${futsal_service.url}")
    private String vendorServiceUrl;
}
