package com.luchkovskiy.service;

import com.luchkovskiy.models.User;

public interface UserService extends CRUDService<Long, User> {

    boolean checkIdExist(Long id);

}
