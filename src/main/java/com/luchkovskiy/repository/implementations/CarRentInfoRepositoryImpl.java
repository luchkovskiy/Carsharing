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
public class CarRentInfoRepositoryImpl implements CarRentInfoRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(CarRentInfo.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public CarRentInfo read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        CarRentInfo carRentInfo = session.get(CarRentInfo.class, id);
        session.flush();
        transaction.commit();
        return carRentInfo;
    }

    @Override
    public List<CarRentInfo> readAll() {
        String query = "SELECT u FROM CarRentInfo u";
        return getCurrentSession().createQuery(query, CarRentInfo.class).getResultList();
    }

    @Override
    public CarRentInfo create(CarRentInfo object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public CarRentInfo update(CarRentInfo object) {
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
