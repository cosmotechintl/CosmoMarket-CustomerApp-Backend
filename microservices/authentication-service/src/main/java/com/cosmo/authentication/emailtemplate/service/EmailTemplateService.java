package com.cosmo.authentication.emailtemplate.service;

import com.cosmo.authentication.emailtemplate.model.CreateEmailTemplate;
import com.cosmo.common.model.ApiResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface EmailTemplateService {
    Mono<ApiResponse> createEmailTemplate(CreateEmailTemplate createEmailTemplate, Principal connectedUser);
}
