package com.ppbkaf.application.services;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;

public interface MailSenderService {

    void send(User user, VerificationToken verificationToken);
}
