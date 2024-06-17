package com.cosmo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@AllArgsConstructor
public class SearchResponseWithMapperBuilder<T, R> {

    private Function<SearchParam, Long> count;
    private Function<SearchParam, List<T>> searchData;
    private SearchParam searchParam;
    private Function<List<T>, List<R>> mapperFunction;
}
