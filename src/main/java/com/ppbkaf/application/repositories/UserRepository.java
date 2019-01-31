package com.ppbkaf.application.repositories;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserRepository {

    @Secured("ROLE_ADMIN")
    List<User> getAll();

    @Secured("ROLE_ADMIN")
    User get(int id);

    User getUserByEmail(String email);

    @Secured("ROLE_ADMIN")
    int add(User obj);

    int register(User obj, VerificationToken verificationToken);

    void activate(String token);

    @Secured("ROLE_ADMIN")
    void update(int id, User obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    User getUserByLogin(String login);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Integer getUserIdByLogin(String login);
}
