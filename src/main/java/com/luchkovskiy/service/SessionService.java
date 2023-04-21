package com.luchkovskiy.service;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;

import java.time.LocalDateTime;

public interface SessionService extends CRUDService<Long, Session> {

    boolean checkIdExist(Long id);

}
