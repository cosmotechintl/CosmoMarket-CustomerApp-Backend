package com.cosmo.common.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BloodGroupDto extends ModelBase {
    @NotBlank(message="Blood group is required")
    private String name;
}
