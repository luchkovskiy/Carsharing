package com.luchkovskiy.service;

import com.luchkovskiy.domain.User;

public interface UserService extends CRUDService<Long, User> {

    boolean checkIdExist(Long id);

}
