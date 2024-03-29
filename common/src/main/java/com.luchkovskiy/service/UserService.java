package com.luchkovskiy.service;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService extends CRUDService<Long, User> {

    List<Role> getUserAuthorities(Long userId);

    Optional<User> findByEmail(String email);

    Page<User> findAllUsersByPage(Pageable pageable);

}
