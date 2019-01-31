package com.ppbkaf.application.aspects;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.repositories.VerificationTokenRepository;
import com.ppbkaf.application.repositories.impl.VerificationTokenRepositoryImpl;
import com.ppbkaf.application.services.MailSenderService;
import com.ppbkaf.application.services.impl.MailSenderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MailAspect {

    private MailSenderService mailSenderService;

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public MailAspect(MailSenderServiceImpl mailSenderService, VerificationTokenRepositoryImpl verificationTokenRepository) {
        this.mailSenderService = mailSenderService;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @AfterReturning(
            value = "execution(public int com.ppbkaf.application.services.impl.UserServiceImpl.register(..)) && args(obj)",
            returning = "id", argNames = "obj,id")
    public void sendRegisterMail(User obj, int id) {
        log.info("Try to send email for user id={}", id);
        VerificationToken verificationToken = this.verificationTokenRepository.getTokenByUser(id);
        try {
            this.mailSenderService.send(obj, verificationToken);
        } catch (Exception e) {
            log.info("Cannot send email");
        }
    }
}
