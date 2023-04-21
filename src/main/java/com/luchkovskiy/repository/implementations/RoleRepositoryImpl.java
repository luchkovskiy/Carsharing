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
public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(Role.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public Role read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Role role = session.get(Role.class, id);
        session.flush();
        transaction.commit();
        return role;
    }

    @Override
    public List<Role> readAll() {
        String query = "SELECT u FROM Role u";
        return getCurrentSession().createQuery(query, Role.class).getResultList();
    }

    @Override
    public Role create(Role object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public Role update(Role object) {
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
