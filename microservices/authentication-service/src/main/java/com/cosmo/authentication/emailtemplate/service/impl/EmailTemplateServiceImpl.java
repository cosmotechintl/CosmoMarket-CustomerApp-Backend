package com.cosmo.authentication.emailtemplate.service.impl;


import com.cosmo.authentication.emailtemplate.entity.EmailTemplate;
import com.cosmo.authentication.emailtemplate.mapper.EmailTemplateMapper;
import com.cosmo.authentication.emailtemplate.model.CreateEmailTemplate;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import com.cosmo.authentication.emailtemplate.service.EmailTemplateService;
import com.cosmo.common.model.ApiResponse;
import com.cosmo.common.util.ResponseUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@RequiredArgsConstructor

public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    @Transactional
    public Mono<ApiResponse> createEmailTemplate(@Valid CreateEmailTemplate createEmailTemplate, Principal connectedUser) {
        EmailTemplate emailTemplate = emailTemplateMapper.mapToEntity(createEmailTemplate);
        emailTemplateRepository.save(emailTemplate);
        return Mono.just(ResponseUtil.getSuccessfulApiResponse("Email Template Created Successfully"));
    }
}
