package com.cosmo.authentication.core.service.impl;

import com.cosmo.authentication.core.service.MailService;
import com.cosmo.authentication.emailtemplate.model.request.SendEmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${MAIL_USERNAME}")
    private String sender;

    @Override
    public void sendEmail(SendEmailRequest sendEmailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(sendEmailRequest.getRecipient());
            mimeMessageHelper.setSubject(sendEmailRequest.getSubject());
            mimeMessageHelper.setText(sendEmailRequest.getMessage(), true); // Set to true to indicate HTML

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
