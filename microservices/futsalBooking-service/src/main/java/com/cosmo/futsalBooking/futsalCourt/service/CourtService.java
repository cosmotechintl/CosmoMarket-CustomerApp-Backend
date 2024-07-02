package com.cosmo.futsalBooking.futsalCourt.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.futsalBooking.futsal.model.FetchFutsalDetail;
import com.cosmo.futsalBooking.futsalCourt.model.FetchCourtByVendorCode;
import reactor.core.publisher.Mono;

public interface CourtService {
    Mono<ApiResponse<Object>> getCourtsByVendorCode(SearchParam searchParam,FetchCourtByVendorCode fetchCourtByVendorCode);
}
