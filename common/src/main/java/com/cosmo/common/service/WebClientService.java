package com.cosmo.common.service;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.WebClientRequestModel;
import reactor.core.publisher.Mono;

public interface WebClientService {
    <T> Mono<ApiResponse<T>> connect(WebClientRequestModel webClientRequestModel);
}

