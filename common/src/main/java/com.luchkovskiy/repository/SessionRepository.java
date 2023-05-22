package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUser(User user);

    @Query("DELETE FROM Session s WHERE s.id = :id")
    @Modifying
    void deleteSession(Long id);

}
