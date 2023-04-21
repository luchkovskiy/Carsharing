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
public class PaymentCardRepositoryImpl implements PaymentCardRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(PaymentCard.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public PaymentCard read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        PaymentCard paymentCard = session.get(PaymentCard.class, id);
        session.flush();
        transaction.commit();
        return paymentCard;
    }

    @Override
    public List<PaymentCard> readAll() {
        String query = "SELECT u FROM PaymentCard u";
        return getCurrentSession().createQuery(query, PaymentCard.class).getResultList();
    }

    @Override
    public PaymentCard create(PaymentCard object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public PaymentCard update(PaymentCard object) {
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
