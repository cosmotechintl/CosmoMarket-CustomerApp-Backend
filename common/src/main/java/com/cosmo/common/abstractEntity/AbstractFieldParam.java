package com.cosmo.common.abstractEntity;

import com.cosmo.common.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFieldParam extends ModelBase {

    protected String fieldKey;

    protected String fieldOperator;
}
