package com.luchkovskiy.repository;

import com.luchkovskiy.models.Session;

public interface SessionRepository extends CRUDRepository<Long, Session> {

    boolean checkIdValid(Long id);

}
