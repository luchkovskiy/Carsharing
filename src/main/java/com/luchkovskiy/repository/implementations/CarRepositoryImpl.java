package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@Primary
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(Car.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public Car read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Car car = session.get(Car.class, id);
        session.flush();
        transaction.commit();
        return car;
    }

    @Override
    public List<Car> readAll() {
        String query = "SELECT u FROM Car u";
        return getCurrentSession().createQuery(query, Car.class).getResultList();
    }

    @Override
    public Car create(Car object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public Car update(Car object) {
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
