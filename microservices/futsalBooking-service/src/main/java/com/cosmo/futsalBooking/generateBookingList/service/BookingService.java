package com.cosmo.futsalBooking.generateBookingList.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourDetailModel;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookingService {
    Mono<ApiResponse<List<BusinessHourDetailModel>>> getAllBookings(BusinessHourBookingModel businessHourBookingModel);
}
