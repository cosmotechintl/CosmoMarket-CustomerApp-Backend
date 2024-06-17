package com.cosmo.common.model;

import com.cosmo.common.abstractEntity.AbstractFieldParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFieldParam extends AbstractFieldParam {

    private String fieldCondition;
    private String fieldValue;

    public SearchFieldParam() {
        this.fieldKey = null;
        this.fieldValue = null;
        this.fieldCondition = null;
        this.fieldOperator = null;
    }

}
