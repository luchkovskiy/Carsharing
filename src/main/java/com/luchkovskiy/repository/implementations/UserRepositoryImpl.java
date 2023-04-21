package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    public boolean checkIdValid(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object entity = session.get(User.class, id);
        session.flush();
        transaction.commit();
        return entity != null;
    }

    @Override
    public User read(Long id) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        User user = session.get(User.class, id);
        session.flush();
        transaction.commit();
        return user;
    }

    @Override
    public List<User> readAll() {
        String query = "SELECT u FROM User u";
        return getCurrentSession().createQuery(query, User.class).getResultList();
    }

    @Override
    public User create(User object) {
        getCurrentSession().save(object);
        return object;
    }

    @Override
    public User update(User object) {
        getCurrentSession().update(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        String query = "UPDATE User set active = false";
        getCurrentSession().createQuery(query, User.class);
    }

    @Override
    public void hardDelete(Long id) {
        getCurrentSession().delete(read(id));
    }

    @Override
    public List<Role> getUserAuthorities(Long userId) {
        Session session = getCurrentSession();
        String hql = "FROM Role r WHERE r.user.id = :userId ORDER BY r.id DESC";
        Query<Role> query = session.createQuery(hql, Role.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = getCurrentSession();
        String hql = "SELECT u FROM User u WHERE u.authenticationInfo.email = :email";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
