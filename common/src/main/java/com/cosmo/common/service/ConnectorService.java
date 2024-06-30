package com.cosmo.common.service;

import com.cosmo.common.model.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

public interface ConnectorService {
    <T> Mono<ApiResponse<T>> connectToService(Object payload, String path, ParameterizedTypeReference<ApiResponse<T>> responseModel);
}
