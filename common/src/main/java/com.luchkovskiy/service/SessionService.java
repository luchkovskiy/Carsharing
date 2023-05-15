package com.luchkovskiy.service;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.models.Session;

public interface SessionService extends CRUDService<Long, Session> {

    Session startSession(Session session, CarRentInfo carRentInfo);

    Session endSession(Session session, CarRentInfo carRentInfo);

}
