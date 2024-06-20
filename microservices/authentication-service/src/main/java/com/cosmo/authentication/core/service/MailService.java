package com.cosmo.authentication.core.service;

import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;

public interface MailService {
    void sendEmail(SendEmailRequest sendEmailRequest);
}
