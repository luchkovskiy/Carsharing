package com.luchkovskiy.service;

import com.luchkovskiy.domain.Car;

public interface CarService extends CRUDService<Long, Car> {

    boolean checkIdExist(Long id);

}
