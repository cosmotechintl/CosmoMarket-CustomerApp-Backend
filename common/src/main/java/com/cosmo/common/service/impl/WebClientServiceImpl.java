package com.cosmo.common.service.impl;

import com.cosmo.common.exception.ConnectionTimeoutException;
import com.cosmo.common.exception.GatewayTimeoutException;
import com.cosmo.common.exception.InternalServerErrorException;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.WebClientRequestModel;
import com.cosmo.common.service.WebClientService;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Slf4j
@Service
public class WebClientServiceImpl implements WebClientService {

    @Autowired
    private WebClient webClient;

    public <T> Mono<ApiResponse<T>> connect(WebClientRequestModel webClientRequestModel) {
        log.info("Web Client Request RequestModel dto :: {}", webClientRequestModel.getRequestModel());

        return webClient
                .method(webClientRequestModel.getHttpMethod())
                .uri(webClientRequestModel.getUri())
                .contentType(webClientRequestModel.getContentType())
                .bodyValue(webClientRequestModel.getRequestModel())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        handle4xxServerError(clientResponse))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        handle5xxServerError(clientResponse))
                .bodyToMono(webClientRequestModel.getParameterizedTypeApiResponse());
    }

    private ApiResponse handleReadTimeout() {
        log.error("Read Timeout Exception Occurred.");
        throw new ReadTimeoutException("Read Timeout Occurred");
    }

    private ApiResponse handleConnectionTimeout() {
        log.error("Connection Timeout Exception Occurred");
        throw new ConnectionTimeoutException("Connection Timeout Occurred");
    }

    private Mono handle4xxServerError(ClientResponse clientResponse) {
        log.error("{} Http Status Code Server Error", clientResponse.statusCode().value());
        throw new RuntimeException(MessageFormat.format("{0} Http Status Code Server Error Occurred.", clientResponse.statusCode().value()));
    }

    private Mono handle5xxServerError(ClientResponse clientResponse) {
        log.error("{} Http Status Code Server Error", clientResponse.statusCode().value());
        if(clientResponse.statusCode() == HttpStatus.GATEWAY_TIMEOUT){
            throw new GatewayTimeoutException("504 Gateway Timeout Occurred.");
        }
        throw new InternalServerErrorException(MessageFormat.format("{0} Http Status Code Server Error Occurred.", clientResponse.statusCode().value()));
    }
}
