package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.VerificationToken;

public interface VerificationTokenRepository {

    public VerificationToken getTokenByUser(int userId);
}
