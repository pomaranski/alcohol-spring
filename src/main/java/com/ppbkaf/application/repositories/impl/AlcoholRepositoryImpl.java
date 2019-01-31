package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.Alcohol;
import com.ppbkaf.application.repositories.AlcoholRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class AlcoholRepositoryImpl implements AlcoholRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public AlcoholRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Alcohol> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Alcohol> alcohols = session.createQuery("FROM Alcohol", Alcohol.class).list();
        return alcohols;
    }

    @Override
    public Alcohol get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Alcohol.class, id);
    }

    @Override
    public int add(Alcohol obj) {
        Session session = this.sessionFactory.getCurrentSession();

        return (int) session.save(obj);
    }

    @Override
    public void update(int id, Alcohol obj) {
        Session session = this.sessionFactory.getCurrentSession();

        Alcohol aAlcohol = session.load(Alcohol.class, id);
        aAlcohol.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Alcohol alcohol = session.load(Alcohol.class, id);
        session.delete(alcohol);
    }

    @Override
    public List<Alcohol> getAllAlcoholsByKind(int kindId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Alcohol> query = session
                .createQuery("FROM Alcohol AS A WHERE A.kind.id = :kindId", Alcohol.class);
        query.setParameter("kindId", kindId);
        return query.getResultList();
    }
}
