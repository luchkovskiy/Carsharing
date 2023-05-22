package com.luchkovskiy.repository;

import com.luchkovskiy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("DELETE FROM Role r WHERE r.id = :id")
    @Modifying
    void deleteRole(Long id);

}
