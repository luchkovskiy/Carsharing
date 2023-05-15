package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentInfoRepository extends JpaRepository<CarRentInfo, Long> {

    CarRentInfo readCarRentInfoByCarId (Long carId);

}
