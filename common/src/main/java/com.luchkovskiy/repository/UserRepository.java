package com.luchkovskiy.repository;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT r FROM Role r INNER JOIN r.user u WHERE u.id = :userId")
    List<Role> getUserAuthorities(Long userId);

    Optional<User> findByAuthenticationInfoEmail(String email);

    @Query("UPDATE User u SET u.active = false WHERE u.id = :id")
    @Modifying
    void inactiveUser(Long id);

    @Procedure
    Timestamp selectLongestSessionDuration(@Param("person_id") Long userId);

    boolean existsByPassportId(String passportId);

    boolean existsByDriverId(String driverId);

    boolean existsByAuthenticationInfoEmail(String email);

}
