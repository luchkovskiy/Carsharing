package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CarRentInfoRepository extends JpaRepository<CarRentInfo, Long> {

    CarRentInfo findCarRentInfoByCarId(Long carId);

    @Query("DELETE FROM CarRentInfo c WHERE c.id = :id")
    @Modifying
    void deleteCarRentInfo(Long id);

}
