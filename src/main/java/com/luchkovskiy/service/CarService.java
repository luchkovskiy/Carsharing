package com.luchkovskiy.service;

import com.luchkovskiy.models.Car;

public interface CarService extends CRUDService<Long, Car> {

    boolean checkIdExist(Long id);

}
