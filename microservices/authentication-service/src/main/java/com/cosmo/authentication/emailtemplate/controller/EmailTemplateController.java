package com.cosmo.authentication.emailtemplate.controller;

import com.cosmo.authentication.emailtemplate.model.CreateEmailTemplate;
import com.cosmo.authentication.emailtemplate.service.EmailTemplateService;
import com.cosmo.common.constant.ApiConstant;
import com.cosmo.common.model.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping(ApiConstant.EMAIL_TEMPLATE)
@RequiredArgsConstructor

public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @PostMapping(ApiConstant.CREATE)
    public Mono<ApiResponse> createEmailTemplate(@RequestBody @Valid CreateEmailTemplate createEmailTemplate, Principal connectedUser){
        return emailTemplateService.createEmailTemplate(createEmailTemplate, connectedUser);
    }
}
