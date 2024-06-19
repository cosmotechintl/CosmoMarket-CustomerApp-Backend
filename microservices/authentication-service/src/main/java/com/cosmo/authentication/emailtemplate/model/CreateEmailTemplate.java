package com.cosmo.authentication.emailtemplate.model;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmailTemplate extends ModelBase {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email template cannot be blank")
    private String template;
}
