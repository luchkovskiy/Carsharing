package com.luchkovskiy.repository;

import com.luchkovskiy.domain.User;

public interface UserRepository extends CRUDRepository<Long, User> {

    boolean checkIdValid(Long id);

    void hardDelete(Long id);

}
