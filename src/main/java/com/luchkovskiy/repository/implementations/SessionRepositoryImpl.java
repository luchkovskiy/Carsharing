package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.repository.*;
import lombok.*;
import org.hibernate.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@Primary
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Boolean checkIdValid(Long id) {
        org.hibernate.Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(Session.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public com.luchkovskiy.models.Session read(Long id) {
        org.hibernate.Session currentSession = getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        transaction.begin();
        com.luchkovskiy.models.Session session = currentSession.get(com.luchkovskiy.models.Session.class, id);
        currentSession.flush();
        transaction.commit();
        return session;
    }

    @Override
    public List<com.luchkovskiy.models.Session> readAll() {
        String query = "SELECT u FROM Session u";
        return getCurrentSession().createQuery(query, com.luchkovskiy.models.Session.class).getResultList();
    }

    @Override
    public com.luchkovskiy.models.Session create(com.luchkovskiy.models.Session object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public com.luchkovskiy.models.Session update(com.luchkovskiy.models.Session object) {
        getCurrentSession().update(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        getCurrentSession().delete(read(id));
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
