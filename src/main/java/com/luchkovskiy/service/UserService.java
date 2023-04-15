package com.luchkovskiy.service;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends CRUDService<Long, User> {

    boolean checkIdExist(Long id);

    List<Role> getUserAuthorities(Long userId);

    Optional<User> findByEmail(String email);

}
