package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.VerificationToken;
import com.ppbkaf.application.repositories.VerificationTokenRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional
public class VerificationTokenRepositoryImpl implements VerificationTokenRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public VerificationTokenRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public VerificationToken getTokenByUser(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<VerificationToken> query = session
                .createQuery("FROM VerificationToken V WHERE V.user.id = :userId", VerificationToken.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }
}
