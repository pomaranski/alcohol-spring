package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.Shop;
import com.ppbkaf.application.repositories.ShopRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ShopRepositoryImpl implements ShopRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ShopRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Shop> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Shop> shops = session.createQuery("FROM Shop", Shop.class).list();
        return shops;
    }

    @Override
    public Shop get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Shop.class, id);
    }

    @Override
    public int add(Shop obj) {
        Session session = this.sessionFactory.getCurrentSession();
        return (int) session.save(obj);
    }

    @Override
    public void update(int id, Shop obj) {
        Session session = this.sessionFactory.getCurrentSession();
        Shop aShop = session.load(Shop.class, id);
        aShop.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Shop shop = session.load(Shop.class, id);
        session.delete(shop);
    }
}
