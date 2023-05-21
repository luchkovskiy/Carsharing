package com.luchkovskiy.service;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.User;

public interface AccidentService extends CRUDService<Long, Accident> {

    User getUserFromAccident(Long sessionId);

    Car getCarFromAccident(Long sessionId);

}
