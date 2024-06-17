package com.cosmo.common.util;

import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.ConnectionRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class ConnectorUtil {

    public static <T> Mono<T> parseToResponseModel(Mono<ApiResponse<T>> apiResponseMono) {
        log.info("Response :::{}", apiResponseMono);
        return apiResponseMono.map(ApiResponse::getData);
    }

    public static <T, U> ConnectionRequestModel<T, U> parseToConnectionRequestModel(String path,
                                                                                    T requestBody,
                                                                                    ParameterizedTypeReference<ApiResponse<U>> parameterizedApiResponse) {
        ConnectionRequestModel<T, U> connectionRequestModel = new ConnectionRequestModel<>();
        connectionRequestModel.setRequestModel(requestBody);
        connectionRequestModel.setPath(path);
        connectionRequestModel.setParameterizedTypeApiResponse(parameterizedApiResponse);
        return connectionRequestModel;
    }

    public static <T, U> Mono<ApiResponse<U>> fetchFromExternalService(WebClient webClient, ConnectionRequestModel<T, U> connectionRequestModel) {
        log.info("Connection Request Model:: {}", connectionRequestModel);
        log.info("Connection Request RequestMode dto :: {}", connectionRequestModel.getRequestModel());
        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder.path(connectionRequestModel.getPath()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(connectionRequestModel.getRequestModel())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("404 unsupported")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("505 server ")))
                .bodyToMono(connectionRequestModel.getParameterizedTypeApiResponse());
    }
}
