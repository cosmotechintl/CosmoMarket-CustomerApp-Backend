package com.cosmo.authentication.core.service;

public interface MailService {
    void sendEmail(String recipient, String subject, String message);
}
