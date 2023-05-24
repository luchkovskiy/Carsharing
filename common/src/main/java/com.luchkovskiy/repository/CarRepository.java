package com.luchkovskiy.repository;

import com.luchkovskiy.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("UPDATE Car c SET c.visible = false WHERE c.id = :id")
    @Modifying
    void inactiveCar(Long id);

    boolean existsByLicensePlateNumber(String licensePlateNumber);

}
