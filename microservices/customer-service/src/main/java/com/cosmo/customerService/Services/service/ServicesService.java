package com.cosmo.customerService.Services.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.SearchParam;
import reactor.core.publisher.Mono;

public interface ServicesService {
    Mono<ApiResponse<Object>> getAll(SearchParam searchParam);
}
