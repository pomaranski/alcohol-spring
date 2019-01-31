package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.Kind;
import com.ppbkaf.application.repositories.KindRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class KindRepositoryImpl implements KindRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public KindRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Kind> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Kind> kinds = session.createQuery("FROM Kind", Kind.class).list();
        return kinds;
    }

    @Override
    public Kind get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Kind.class, id);
    }


    @Override
    public int add(Kind obj) {
        Session session = this.sessionFactory.getCurrentSession();
        return (int) session.save(obj);
    }


    @Override
    public void update(int id, Kind obj) {
        Session session = this.sessionFactory.getCurrentSession();
        Kind aKind = session.load(Kind.class, id);
        aKind.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Kind kind = session.load(Kind.class, id);
        session.delete(kind);
    }
}
