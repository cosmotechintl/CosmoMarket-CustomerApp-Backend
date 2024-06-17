package com.cosmo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@AllArgsConstructor
public class SearchResponseBuilder<R> {

    private Function<SearchParam, Long> count;
    private Function<SearchParam, List<R>> searchData;
    private SearchParam searchParam;
}
