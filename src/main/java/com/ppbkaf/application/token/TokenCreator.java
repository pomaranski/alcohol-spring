package com.ppbkaf.application.token;

import com.ppbkaf.application.entities.VerificationToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TokenCreator {

    public TokenCreator() {

    }

    public VerificationToken create() {
        UUID uuid = UUID.randomUUID();
        return VerificationToken.builder()
                .time(LocalDateTime.now())
                .token(uuid.toString())
                .build();
    }
}
