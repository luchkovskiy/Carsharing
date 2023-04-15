package com.luchkovskiy.repository;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CRUDRepository<Long, User> {

    boolean checkIdValid(Long id);

    void hardDelete(Long id);

    List<Role> getUserAuthorities(Long userId);

    Optional<User> findByEmail (String email);

}
