package com.cosmo.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;

@Getter
@Setter
public class ConnectionRequestModel<T, U> extends ModelBase {
    private String path;
    private T requestModel;
    private ParameterizedTypeReference<ApiResponse<U>> parameterizedTypeApiResponse;
}
