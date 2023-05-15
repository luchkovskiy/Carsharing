package com.luchkovskiy.repository;

import com.luchkovskiy.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
