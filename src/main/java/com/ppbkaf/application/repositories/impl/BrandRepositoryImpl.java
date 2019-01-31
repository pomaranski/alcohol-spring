package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.Brand;
import com.ppbkaf.application.repositories.BrandRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BrandRepositoryImpl implements BrandRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public BrandRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Brand> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Brand> brands = session.createQuery("FROM Brand", Brand.class).list();
        return brands;
    }

    @Override
    public Brand get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Brand.class, id);
    }

    @Override
    public int add(Brand obj) {
        Session session = this.sessionFactory.getCurrentSession();
        return (int) session.save(obj);
    }

    @Override
    public void update(int id, Brand obj) {
        Session session = this.sessionFactory.getCurrentSession();
        Brand aBrand = session.load(Brand.class, id);
        aBrand.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Brand brand = session.load(Brand.class, id);
        session.delete(brand);
    }
}
