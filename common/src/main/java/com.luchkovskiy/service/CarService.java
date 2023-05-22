package com.luchkovskiy.service;

import com.luchkovskiy.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService extends CRUDService<Long, Car> {

    Page<Car> findAllCarsByPage(Pageable pageable);

}
