package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarClassLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarClassRepository extends JpaRepository<CarClassLevel, Long> {

}
