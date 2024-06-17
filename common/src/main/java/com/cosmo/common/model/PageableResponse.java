package com.cosmo.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> extends ModelBase {
    private Integer total;
    private List<T> records;
}
