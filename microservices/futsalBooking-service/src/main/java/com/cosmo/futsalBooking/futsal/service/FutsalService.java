package com.cosmo.futsalBooking.futsal.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.futsal.model.FetchFutsalDetail;
import reactor.core.publisher.Mono;

public interface FutsalService {
    Mono<ApiResponse<Object>> getFutsalDetails(FetchFutsalDetail fetchFutsalDetail);
}
