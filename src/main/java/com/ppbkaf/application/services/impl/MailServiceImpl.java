package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.repositories.VerificationTokenRepository;
import com.ppbkaf.application.services.MailSenderService;
import com.ppbkaf.application.services.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private VerificationTokenRepository verificationTokenRepository;

    private UserRepository userRepository;

    private MailSenderService mailSenderService;

    @Autowired
    public MailServiceImpl(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository,
                           MailSenderService mailSenderService) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void resend(String email) {
        try {
            User user = this.userRepository.getUserByEmail(email);
            VerificationToken verificationToken = this.verificationTokenRepository.getTokenByUser(user.getId());
            this.mailSenderService.send(user, verificationToken);
        } catch (Exception ex) {
            log.error("Cannot resend email to user", ex);
            throw new GetException("Cannot resend email", ex);
        }
    }
}
