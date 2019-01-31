package com.ppbkaf.application.services.impl.util;

import com.ppbkaf.application.authentication.AuthenticationFacade;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.repositories.impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserDetectionService {

    private UserRepository userRepository;

    private AuthenticationFacade authenticationFacade;

    @Autowired
    public UserDetectionService(UserRepositoryImpl userRepository, AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    public User getUser() {
        return this.userRepository.getUserByLogin(this.getLogin());
    }

    public int getUserId() {
        return this.userRepository.getUserIdByLogin(this.getLogin());
    }

    private String getLogin() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authenticationFacade.getAuthentication().getAuthorities();
    }

    public boolean isAdmin() {
        return this.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }
}
