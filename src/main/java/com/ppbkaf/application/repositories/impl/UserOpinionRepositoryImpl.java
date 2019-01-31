package com.ppbkaf.application.repositories.impl;

import com.ppbkaf.application.dtos.UserOpinionDTO;
import com.ppbkaf.application.entities.Sale;
import com.ppbkaf.application.entities.User;
import com.ppbkaf.application.entities.UserOpinion;
import com.ppbkaf.application.repositories.UserOpinionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserOpinionRepositoryImpl implements UserOpinionRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public UserOpinionRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int like(int saleId, int userId) {
        return changeOpinion(saleId, userId, 1);
    }

    @Override
    public int dislike(int saleId, int userId) {
        return changeOpinion(saleId, userId, -1);
    }

    private int changeOpinion(int saleId, int userId, int opinion) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT UO.id FROM UserOpinion UO WHERE UO.sale.id=:saleId AND UO.user.id=:userId",
                Integer.class);
        query.setParameter("saleId", saleId);
        query.setParameter("userId", userId);

        Integer id = (Integer) query.uniqueResult();
        UserOpinion userOpinion;

        if (id == null) {
            userOpinion = UserOpinion.builder().opinion(opinion).build();
            userOpinion.setSale(session.load(Sale.class, saleId));
            userOpinion.setUser(session.load(User.class, userId));
            return (int) session.save(userOpinion);
        } else {
            userOpinion = session.load(UserOpinion.class, id);
            userOpinion.setOpinion(opinion);
            return id;
        }
    }

    @Override
    public List<UserOpinionDTO> getAllByUser(int userId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("SELECT new com.ppbkaf.application.dtos.UserOpinionDTO(UO.id, UO.sale.id, UO.opinion) " +
                "FROM UserOpinion UO WHERE UO.user.id=:userId", UserOpinionDTO.class);
        query.setParameter("userId", userId);

        List<UserOpinionDTO> userOpinionDTOS = query.getResultList();
        return userOpinionDTOS;
    }
}
