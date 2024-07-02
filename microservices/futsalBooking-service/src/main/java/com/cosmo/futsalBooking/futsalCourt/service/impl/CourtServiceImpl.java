package com.cosmo.futsalBooking.futsalCourt.service.impl;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.constant.ServiceConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.service.AbstractConnectorService;
import com.cosmo.common.service.ConnectorService;
import com.cosmo.common.service.PropertiesFileValue;
import com.cosmo.futsalBooking.futsal.service.FutsalService;
import com.cosmo.futsalBooking.futsalCourt.model.FetchCourtByVendorCode;
import com.cosmo.futsalBooking.futsalCourt.service.CourtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Qualifier(ServiceConstant.COURT_SERVICE)
@RequiredArgsConstructor
@Service
@Slf4j
public class CourtServiceImpl extends AbstractConnectorService implements CourtService, ConnectorService {
    private final PropertiesFileValue propertiesFileValue;

    @Override
    protected String getBaseUrl() {
        return propertiesFileValue.getFutsalServiceUrl();
    }
    @Override
    public Mono<ApiResponse<Object>> getCourtsByVendorCode(SearchParam searchParam, FetchCourtByVendorCode fetchCourtByVendorCode) {
        log.info("Vendor Code :::{}", fetchCourtByVendorCode);
        Map<String, Object> payload = new HashMap<>();
        payload.put("searchParam", searchParam);
        payload.put("fetchCourtByVendorCode", fetchCourtByVendorCode);
        return connectToService(payload,
                ApiConstant.COURT+ApiConstant.SLASH+ApiConstant.GET_BY_CODE,
                new ParameterizedTypeReference<>() {
                });
    }
}
