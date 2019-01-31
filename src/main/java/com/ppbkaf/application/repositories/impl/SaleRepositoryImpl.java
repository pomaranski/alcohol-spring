package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.repositories.SaleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class SaleRepositoryImpl implements SaleRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public SaleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Sale> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Sale> sales = session.createQuery("FROM Sale", Sale.class).list();
        return sales;
    }

    @Override
    public Sale get(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.get(Sale.class, id);
    }

    @Override
    public int add(Sale obj) {
        Session session = this.sessionFactory.getCurrentSession();
        return (int) session.save(obj);
    }

    @Override
    public void update(int id, Sale obj) {
        Session session = this.sessionFactory.getCurrentSession();

        Sale aSale = session.load(Sale.class, id);
        aSale.copy(obj);
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Sale sale = session.load(Sale.class, id);
        session.delete(sale);
    }

    @Override
    public List<Sale> getAllSalesByShopAndKind(int shopId, int kindId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Sale> query = session
                .createQuery("FROM Sale AS S WHERE S.shop.id = :shopId AND S.alcohol.kind.id = :kindId", Sale.class);
        query.setParameter("shopId", shopId);
        query.setParameter("kindId", kindId);
        return query.getResultList();
    }

    @Override
    public List<Sale> getAllSalesByShop(int shopId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Sale> query = session
                .createQuery("FROM Sale AS S WHERE S.shop.id = :shopId", Sale.class);
        query.setParameter("shopId", shopId);
        return query.getResultList();
    }

    @Override
    public List<Sale> getAllSalesByKind(int kindId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Sale> query = session
                .createQuery("FROM Sale AS S WHERE S.alcohol.kind.id = :kindId", Sale.class);
        query.setParameter("kindId", kindId);
        return query.getResultList();
    }

    @Override
    public List<Sale> getAllSalesByUser(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Sale> query = session
                .createQuery("FROM Sale AS S WHERE S.user.id = :userId", Sale.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public int getOwnerId(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        TypedQuery<Integer> query = session
                .createQuery("SELECT S.user.id FROM Sale AS S WHERE S.id = :id", Integer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
