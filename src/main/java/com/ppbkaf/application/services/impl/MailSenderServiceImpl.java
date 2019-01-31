package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.dtos.MailDTO;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.exceptions.MailServiceException;
import com.ppbkaf.application.services.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailSenderServiceImpl implements MailSenderService {

    private JavaMailSender mailSender;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(User user, VerificationToken verificationToken) {
        MailDTO mailDTO = prepareMail(user, verificationToken);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailDTO.getEmail());
            message.setSubject(mailDTO.getSubject());
            message.setText(mailDTO.getContent());
            mailSender.send(message);
        } catch (MailSendException ex) {
            log.error("Cannot send", ex);
            throw new MailServiceException();
        } catch (MailAuthenticationException ex) {
            log.error("Cannot authenticate", ex);
            throw new MailServiceException();
        } catch (MailException ex) {
            log.error("Mail exception", ex);
            throw new MailServiceException();
        }
    }

    private static MailDTO prepareMail(User user, VerificationToken verificationToken) {
        return MailDTO.builder()
                .email(user.getEmail())
                .subject("Activation link")
                .content("Click this link to activate account: https://alco-app.herokuapp.com/activate?token="
                        + verificationToken.getToken()).build();
    }
}
