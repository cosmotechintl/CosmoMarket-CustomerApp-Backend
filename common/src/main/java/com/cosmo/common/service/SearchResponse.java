package com.cosmo.common.service;


import com.cosmo.common.model.PageableResponse;
import com.cosmo.common.model.SearchResponseBuilder;
import com.cosmo.common.model.SearchResponseWithMapperBuilder;

import java.util.List;

public interface SearchResponse {

    <T> PageableResponse<T> getSearchResponse(List<T> record, Integer totalCount);

    <R> PageableResponse<R> getSearchResponse(SearchResponseBuilder<R> searchResponseBuilder);

    <T, R> PageableResponse<R> getSearchResponse(SearchResponseWithMapperBuilder<T, R> responseWithMapperBuilder);
}
