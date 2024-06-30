package com.cosmo.futsalBooking.futsal.controller;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.futsalBooking.futsal.model.FetchFutsalDetail;
import com.cosmo.futsalBooking.futsal.service.FutsalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.FUTSAL)
@RequiredArgsConstructor
public class FutsalController {
    private final FutsalService futsalService;
    @PostMapping(ApiConstant.GET+ApiConstant.SLASH+ApiConstant.DETAIL)
    public Mono<ApiResponse<Object>> getCustomerDetails(@RequestBody @Valid FetchFutsalDetail fetchFutsalDetail){
        return futsalService.getFutsalDetails(fetchFutsalDetail);
    }
}
