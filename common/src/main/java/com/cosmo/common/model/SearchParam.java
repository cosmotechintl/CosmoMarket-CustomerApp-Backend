package com.cosmo.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SearchParam extends ModelBase {

    private Integer firstRow;
    private Integer pageSize;
    private List<SearchFieldParam> searchFieldParams;

    private Map<String, Object> param = new HashMap<>();


    public Integer getFirstRow() {
        if (firstRow == null) {
            return 1;
        }
        return firstRow;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            return 10;
        }
        return pageSize;
    }
}
