package com.cosmo.futsalBooking.generateBookingList.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHour;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookingService {
    public Mono<ApiResponse> createBookingsLists(BusinessHourBookingModel businessHourBookingModel);
}
