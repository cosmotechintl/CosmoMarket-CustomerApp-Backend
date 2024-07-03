package com.cosmo.customerService.Services.controller;

import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.customerService.Services.service.ServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiConstant.SERVICES)
@RequiredArgsConstructor
public class ServicesController {
    private final ServicesService servicesService;
    @PostMapping(ApiConstant.GET_ALL_SERVICES)
    public Mono<ApiResponse<Object>> getAllServices(@RequestBody @Valid SearchParam searchParam ){
        return servicesService.getAll(searchParam);
    }
}
