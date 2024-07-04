package com.cosmo.futsalBooking.generateBookingList.controller;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.generateBookingList.model.BusinessHourBookingModel;
import com.cosmo.futsalBooking.generateBookingList.service.FutsalBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.SLOTS)
@RequiredArgsConstructor
public class FutsalBookingController {
    public final FutsalBookingService futsalBookingService;

    @PostMapping(ApiConstant.CREATE)
    public Mono<ApiResponse> creatingBookingLists(@RequestBody @Valid BusinessHourBookingModel businessHourBookingModel){
        return futsalBookingService.createBookingsLists(businessHourBookingModel);
    }
}
