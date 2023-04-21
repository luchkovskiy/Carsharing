package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.models.User;

import java.time.LocalDateTime;

public interface SessionRepository extends CRUDRepository<Long, Session> {

    Boolean checkIdValid(Long id);


}
