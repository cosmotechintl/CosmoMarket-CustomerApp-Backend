package com.cosmo.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDto extends ModelBase {

    private String name;
    private String description;
    private String icon;
}
