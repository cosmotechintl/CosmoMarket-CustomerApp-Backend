package com.cosmo.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class WebClientRequestModel<T> extends ModelBase {

    private URI uri;
    private Class returnClass;
    private HttpMethod httpMethod;
    private Object requestModel;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
    @Builder.Default
    private MediaType contentType = MediaType.APPLICATION_JSON;
    private HttpHeaders requestHeaders;
    private ParameterizedTypeReference<ApiResponse<T>> parameterizedTypeApiResponse;
}
