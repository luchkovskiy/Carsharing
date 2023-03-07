package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Car;

public interface CarRepository extends CRUDRepository<Long, Car> {

    boolean checkIdValid(Long id);
}
