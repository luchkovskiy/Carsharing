package com.luchkovskiy.repository;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface AccidentRepository extends JpaRepository<Accident, Long> {

    @Query("SELECT u " +
            "FROM User u " +
            "JOIN u.sessions s " +
            "JOIN s.accidents a " +
            "WHERE a.session.id = :sessionId")
    User getUserFromAccident(Long sessionId);

    @Query("SELECT c " +
            "FROM Car c " +
            "JOIN c.sessions s " +
            "JOIN s.accidents a " +
            "WHERE s.id = :sessionId")
    Car getCarFromAccident(Long sessionId);

    @Query("DELETE FROM Accident a WHERE a.id = :id")
    @Modifying
    void deleteAccident(Long id);

}
