package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.repository.AccidentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class AccidentRepositoryImpl implements AccidentRepository {

    // TODO: 18.04.2023 Переделать репозитории на hibernate

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(Accident.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public List<Accident> getAccidentsBySession(Long sessionId) {
        String query = "SELECT u FROM Accident u WHERE u.session = " + sessionId;
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        List<Accident> list = session.createQuery(query, Accident.class).list();
        session.flush();
        transaction.commit();
        return list;
    }

    @Override
    public Accident read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Accident accident = session.get(Accident.class, id);
        session.flush();
        transaction.commit();
        return accident;
    }

    @Override
    public List<Accident> readAll() {
        String query = "SELECT u FROM Accident u";
        return getCurrentSession().createQuery(query, Accident.class).getResultList();
    }

    @Override
    public Accident create(Accident object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public Accident update(Accident object) {
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
