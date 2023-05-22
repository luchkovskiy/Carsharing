package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;


public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByUser(User user);

    @Query("DELETE FROM Session s WHERE s.id = :id")
    @Modifying
    void deleteSession(Long id);

    @Procedure
    Integer countSessionAccidents(@Param("session") Long sessionId);

}
