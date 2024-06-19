package com.cosmo.futsalBooking.generateBookingList.controller;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourDetailModel;
import com.cosmo.futsalBooking.generateBookingList.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(ApiConstant.SLOTS)
@RequiredArgsConstructor
public class BookingController {
    public final BookingService bookingService;

    @PostMapping(ApiConstant.GET)
    public Mono<ApiResponse<List<BusinessHourDetailModel>>> getAllBookings(@RequestBody @Valid BusinessHourBookingModel businessHourBookingModel){
        return bookingService.getAllBookings(businessHourBookingModel);
    }
}
