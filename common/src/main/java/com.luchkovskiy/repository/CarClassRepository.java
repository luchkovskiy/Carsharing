package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarClassRepository extends JpaRepository<CarClass, Long> {

}
