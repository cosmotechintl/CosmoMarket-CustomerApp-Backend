package com.cosmo.authentication.emailtemplate.model.request;

import com.cosmo.common.model.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest extends ModelBase {
    @Email(message="Invalid email format")
    private String recipient;
    @NotBlank(message="Subject cannot be empty")
    private String subject;
    @NotBlank(message="Message cannot be empty")
    private String message;
}
