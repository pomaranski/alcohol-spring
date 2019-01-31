package com.ppbkaf.application.services.impl;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.exceptions.AddException;
import com.ppbkaf.application.exceptions.DeleteException;
import com.ppbkaf.application.exceptions.GetException;
import com.ppbkaf.application.exceptions.UpdateException;
import com.ppbkaf.application.repositories.UserRepository;
import com.ppbkaf.application.services.UserService;
import com.ppbkaf.application.token.TokenCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private TokenCreator tokenCreator;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenCreator tokenCreator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenCreator = tokenCreator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        try {
            return this.userRepository.getAll();
        } catch (Exception ex) {
            log.info("Cannot get all", ex);
            throw new GetException("Cannot get all users", ex);
        }
    }

    @Override
    public User get(int id) {
        try {
            return this.userRepository.get(id);
        } catch (Exception ex) {
            log.info("Cannot get {}", id, ex);
            throw new GetException("Cannot get user", ex);
        }
    }

    @Override
    public int add(User obj) {
        try {
            return this.userRepository.add(obj);
        } catch (Exception ex) {
            log.info("Cannot add {}", obj, ex);

            throw new AddException("Cannot add user", ex);
        }
    }

    @Override
    public int register(User obj) {
        obj.setActivated(false);
        obj.setAdmin(false);
        obj.setRole("USER_ROLE");
        obj.setPassword(this.passwordEncoder.encode(obj.getPassword()));
        try {
            VerificationToken verificationToken = tokenCreator.create();
            return this.userRepository.register(obj, verificationToken);
        } catch (Exception ex) {
            log.info("Cannot register {}", obj, ex);
            throw new AddException("Cannot register user", ex);
        }
    }

    @Override
    public void activate(String token) {
        try {
            this.userRepository.activate(token);
        } catch (Exception ex) {
            log.info("Cannot activate user by token={}", token, ex);
            throw new UpdateException("Cannot activate user", ex);
        }
    }

    @Override
    public void update(int id, User obj) {
        try {
            this.userRepository.update(id, obj);
        } catch (Exception ex) {
            log.info("Cannot update id={} on {}", id, obj, ex);
            throw new UpdateException("Cannot update user", ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            this.userRepository.delete(id);
        } catch (Exception ex) {
            log.info("Cannot delete id={}", id, ex);
            throw new DeleteException("Cannot delete user", ex);
        }
    }
}
