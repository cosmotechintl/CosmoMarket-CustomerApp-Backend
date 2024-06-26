package com.cosmo.common.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenderDto extends ModelBase {
    @NotBlank(message = "Gender is required")
    private String name;
}
