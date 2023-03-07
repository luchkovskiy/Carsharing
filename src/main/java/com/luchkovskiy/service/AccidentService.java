package com.luchkovskiy.service;

import com.luchkovskiy.domain.Accident;

import java.util.List;

public interface AccidentService extends CRUDService<Long, Accident> {

    boolean checkIdExist(Long id);

    List<Accident> getAccidentsBySession(Long sessionId);
}
