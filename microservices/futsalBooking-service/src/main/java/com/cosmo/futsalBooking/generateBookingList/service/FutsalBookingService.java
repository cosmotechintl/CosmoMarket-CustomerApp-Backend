package com.cosmo.futsalBooking.generateBookingList.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BookFutsalModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface FutsalBookingService {
    Mono<ApiResponse> createBookingsLists(BusinessHourBookingModel businessHourBookingModel);
    Mono<ApiResponse> bookFutsal(BookFutsalModel bookFutsalModel, Principal connectedUser);
}
