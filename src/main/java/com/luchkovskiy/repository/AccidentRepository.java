package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Accident;

import java.util.List;


public interface AccidentRepository extends CRUDRepository<Long, Accident> {

    boolean checkIdValid(Long id);

    List<Accident> getAccidentsBySession(Long sessionId);

}
