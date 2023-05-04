package com.luchkovskiy.repository;

import com.luchkovskiy.models.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query (value = "SELECT r FROM Role r INNER JOIN r.user u WHERE u.id = :userId")
    List<Role> getUserAuthorities(Long userId);

    Optional<User> findByAuthenticationInfoEmail (String email);

}
