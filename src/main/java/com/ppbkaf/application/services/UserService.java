package com.ppbkaf.application.services;

import com.ppbkaf.application.entities.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface UserService {

    @Secured("ROLE_ADMIN")
    List<User> getAll();

    @Secured("ROLE_ADMIN")
    User get(int id);

    @Secured("ROLE_ADMIN")
    int add(User obj);

    int register(User obj);

    void activate(String token);

    @Secured("ROLE_ADMIN")
    void update(int id, User obj);

    @Secured("ROLE_ADMIN")
    void delete(int id);
}
