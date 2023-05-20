package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUser(User user);

}
