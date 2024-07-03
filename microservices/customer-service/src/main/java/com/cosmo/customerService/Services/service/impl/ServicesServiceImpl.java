package com.cosmo.customerService.Services.service.impl;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.constant.ServiceConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.service.AbstractConnectorService;
import com.cosmo.common.service.ConnectorService;
import com.cosmo.common.service.PropertiesFileValue;
import com.cosmo.customerService.Services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Qualifier(ServiceConstant.SERVICES)
@RequiredArgsConstructor
@Service
@Slf4j
public class ServicesServiceImpl extends AbstractConnectorService implements ServicesService, ConnectorService {

    private final PropertiesFileValue propertiesFileValue;

    @Override
    protected String getBaseUrl() {
        return propertiesFileValue.getServicesServiceUrl();
    }
    @Override
    public Mono<ApiResponse<Object>> getAll(SearchParam searchParam) {
        return connectToService(searchParam,
                ApiConstant.SERVICES + ApiConstant.SLASH + ApiConstant.GET_ALL_SERVICES,
        new ParameterizedTypeReference<>() {
        });
    }
}