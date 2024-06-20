package com.cosmo.authentication.util;

import com.cosmo.authentication.core.constant.EmailTemplateConstant;
import com.cosmo.authentication.core.constant.FreeMarkerTemplateConstant;
import com.cosmo.authentication.emailtemplate.entity.EmailTemplate;
import com.cosmo.authentication.emailtemplate.repo.EmailTemplateRepository;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailContentUtil {
    @Autowired
    private EmailTemplateRepository emailTemplateRepository;
    @Autowired
    private freemarker.template.Configuration freeMarkerConfig;

    public String prepareEmailContent(String customerName, String otp) {
        EmailTemplate emailTemplate = emailTemplateRepository.findEmailTemplateByName(EmailTemplateConstant.MAIL_VERIFICATION);
        Map<String, Object> model = new HashMap<>();
        model.put(FreeMarkerTemplateConstant.USERNAME, customerName);
        model.put(FreeMarkerTemplateConstant.OTP, otp);
        model.put(FreeMarkerTemplateConstant.CURRENT_YEAR, Year.now().getValue());

        String emailContent;
        try {
            Template template = new Template("emailTemplate", emailTemplate.getTemplate(), freeMarkerConfig);
            emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new RuntimeException("Error processing email template: "+ e.getMessage());
        }

        return emailContent;
    }
}
