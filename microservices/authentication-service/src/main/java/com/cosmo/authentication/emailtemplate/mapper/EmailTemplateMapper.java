package com.cosmo.authentication.emailtemplate.mapper;

import com.cosmo.authentication.emailtemplate.entity.EmailTemplate;
import com.cosmo.authentication.emailtemplate.model.CreateEmailTemplate;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EmailTemplateMapper {

    @Autowired
    protected EmailTemplateRepository emailTemplateRepository;

    public EmailTemplate mapToEntity(CreateEmailTemplate createEmailTemplate){
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setName(createEmailTemplate.getName());
        emailTemplate.setTemplate(createEmailTemplate.getTemplate());
        emailTemplate.setCreatedDate(new Date());
        return emailTemplate;
    }
}
