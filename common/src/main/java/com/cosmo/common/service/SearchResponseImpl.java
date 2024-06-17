package com.cosmo.common.service;

import com.cosmo.common.exception.InternalServerErrorException;
import com.cosmo.common.model.PageableResponse;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.model.SearchResponseBuilder;
import com.cosmo.common.model.SearchResponseWithMapperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchResponseImpl implements SearchResponse {

    @Override
    public <T> PageableResponse<T> getSearchResponse(List<T> record, Integer totalCount) {
        PageableResponse<T> pageableResponse = new PageableResponse<>();
        pageableResponse.setRecords(record);
        pageableResponse.setTotal(totalCount);
        return pageableResponse;
    }

    @Override
    public <R> PageableResponse<R> getSearchResponse(SearchResponseBuilder<R> searchResponseBuilder) {
        try {
            Long totalCount = searchResponseBuilder.getCount().apply(searchResponseBuilder.getSearchParam());
            if (!totalCount.equals(0L)) {
                List<R> record = searchResponseBuilder.getSearchData().apply(searchResponseBuilder.getSearchParam());
                return getSearchResponse(record, totalCount.intValue());
            }
            return getSearchResponse(new ArrayList<>(), totalCount.intValue());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            throw new InternalServerErrorException();
        }
    }

    @Override
    public <T, R> PageableResponse<R> getSearchResponse(SearchResponseWithMapperBuilder<T, R> responseWithMapperBuilder) {
        try {
            SearchParam searchParam = responseWithMapperBuilder.getSearchParam();
            Long totalCount = responseWithMapperBuilder.getCount().apply(searchParam);
            log.debug("totalCount:{}", totalCount);
            if (!totalCount.equals(0L)) {
                List<T> record = responseWithMapperBuilder.getSearchData().apply(searchParam);
                log.debug("recordSize:{}", record.size());
                List<R> mappedRecord = responseWithMapperBuilder.getMapperFunction().apply(record);
                return getSearchResponse(mappedRecord, totalCount.intValue());
            }
            return getSearchResponse(new ArrayList<>(), totalCount.intValue());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException();
        }
    }

}
