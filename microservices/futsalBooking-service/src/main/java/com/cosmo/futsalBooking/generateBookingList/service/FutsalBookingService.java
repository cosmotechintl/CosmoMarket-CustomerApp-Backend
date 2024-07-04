package com.cosmo.futsalBooking.generateBookingList.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import reactor.core.publisher.Mono;

public interface FutsalBookingService {
    public Mono<ApiResponse> createBookingsLists(BusinessHourBookingModel businessHourBookingModel);
}
