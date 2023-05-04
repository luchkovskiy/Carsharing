package com.luchkovskiy.repository;

import com.luchkovskiy.models.Car;

public interface CarRepository extends CRUDRepository<Long, Car> {

    boolean checkIdValid(Long id);
}
