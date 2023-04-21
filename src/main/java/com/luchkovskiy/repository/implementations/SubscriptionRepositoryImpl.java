package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(Subscription.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public Subscription read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Subscription subscription = session.get(Subscription.class, id);
        session.flush();
        transaction.commit();
        return subscription;
    }

    @Override
    public List<Subscription> readAll() {
        String query = "SELECT u FROM Subscription u";
        return getCurrentSession().createQuery(query, Subscription.class).getResultList();
    }

    @Override
    public Subscription create(Subscription object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public Subscription update(Subscription object) {
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
