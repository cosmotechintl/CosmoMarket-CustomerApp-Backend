package com.cosmo.common.service;

import com.cosmo.common.exception.ConnectionTimeoutException;
import com.cosmo.common.exception.GatewayTimeoutException;
import com.cosmo.common.exception.InternalServerErrorException;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.model.WebClientRequestModel;
import com.cosmo.common.util.ResponseUtil;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;

import java.net.URI;

@Slf4j
public abstract class AbstractConnectorService {

    @Autowired
    private WebClientService webClientService;

    protected abstract String getBaseUrl();

    public <T> Mono<ApiResponse<T>> connectToService(Object payload, String path, ParameterizedTypeReference<ApiResponse<T>> responseModel) {

        WebClientRequestModel webClientRequestModel = WebClientRequestModel.builder()
                .uri(getURI(path))
                .requestModel(payload)
                .httpMethod(HttpMethod.POST)
                .requestHeaders(getHttpHeaders()).build();
        setParameterizedTypeReference(webClientRequestModel, responseModel);
        try {
            Mono<ApiResponse<T>> apiResponseMono = webClientService.connect(webClientRequestModel);
            log.info("Response :::{}", apiResponseMono);
            return apiResponseMono;
        } catch (Exception ex) {
            if (ex instanceof ConnectionTimeoutException) {
                return getFailureResponse();
            } else if (ex instanceof GatewayTimeoutException || ex instanceof ReadTimeoutException || ex instanceof InternalServerErrorException) {
                return getTimeoutResponse();
            }
        }
        throw new RuntimeException("Error Occurred");
    }

    private <T> void setParameterizedTypeReference(WebClientRequestModel<T> webClientRequestModel, ParameterizedTypeReference<ApiResponse<T>> responseModel) {
        webClientRequestModel.setParameterizedTypeApiResponse(responseModel);
    }

    protected URI getURI(String path) {
        String baseUrl = getBaseUrl();
        return UriComponentsBuilder.fromUriString(baseUrl + path).build().toUri();
    }

    protected HttpHeaders getHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return requestHeaders;
    }

    protected <T> Mono<ApiResponse<T>> getFailureResponse() {
        return MonoOperator.just(ResponseUtil.getFailureResponse("Failed to Get Response"));
    }

    protected <T> Mono<ApiResponse<T>> getTimeoutResponse() {
        return MonoOperator.just(ResponseUtil.getTimeoutApiResponse("Timeout Occurred."));
    }
}
