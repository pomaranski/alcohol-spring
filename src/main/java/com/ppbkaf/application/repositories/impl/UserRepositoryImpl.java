package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("FROM User", User.class).list();
        return users;
    }

    @Override
    public User get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<User> query =
                session.createQuery("FROM User U WHERE U.email = :email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public int add(User obj) {
        Session session = this.sessionFactory.getCurrentSession();
        return (int) session.save(obj);
    }

    @Override
    public int register(User obj, VerificationToken verificationToken) {
        Session session = this.sessionFactory.getCurrentSession();
        int id = (int) session.save(obj);
        verificationToken.setUser(obj);
        session.save(verificationToken);
        return id;
    }

    @Override
    public void activate(String token) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Integer> query =
                session.createQuery("SELECT V.user.id FROM VerificationToken V WHERE V.token = :token", Integer.class);
        query.setParameter("token", token);
        int userId = query.getSingleResult();
        log.info("Try to activate user id={}", userId);
        User user = session.load(User.class, userId);
        user.setActivated(true);
    }

    @Override
    public void update(int id, User obj) {
        Session session = this.sessionFactory.getCurrentSession();
        User aUser = session.load(User.class, id);
        aUser.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = session.load(User.class, id);
        session.delete(user);
    }

    @Override
    public User getUserByLogin(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<User> query =
                session.createQuery("SELECT U FROM User U WHERE U.login = :login", User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @Override
    public Integer getUserIdByLogin(String login) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Integer> query =
                session.createQuery("SELECT U.id FROM User U WHERE U.login = :login", Integer.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }
}
