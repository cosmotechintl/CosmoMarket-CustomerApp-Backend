package com.cosmo.futsalBooking.futsal.service.impl;

import com.cosmo.common.service.PropertiesFileValue;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.constant.ServiceConstant;
import com.cosmo.common.service.AbstractConnectorService;
import com.cosmo.common.service.ConnectorService;
import com.cosmo.futsalBooking.futsal.model.FetchFutsalDetail;
import com.cosmo.futsalBooking.futsal.service.FutsalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Qualifier(ServiceConstant.FUTSAL_SERVICE)
@RequiredArgsConstructor
@Service
@Slf4j
public class FutsalServiceImpl extends AbstractConnectorService implements FutsalService, ConnectorService {

    private final PropertiesFileValue propertiesFileValue;

    @Override
    protected String getBaseUrl() {
        return propertiesFileValue.getFutsalServiceUrl();
    }
    @Override
    public Mono<ApiResponse<Object>> getFutsalDetails(FetchFutsalDetail fetchFutsalDetail) {
        log.info("Vendor Code :::{}", fetchFutsalDetail);
        return connectToService(fetchFutsalDetail,
                ApiConstant.FUTSAL+ApiConstant.SLASH+ApiConstant.GET+ApiConstant.SLASH+ApiConstant.DETAIL,
                new ParameterizedTypeReference<>() {
                });
    }
}
