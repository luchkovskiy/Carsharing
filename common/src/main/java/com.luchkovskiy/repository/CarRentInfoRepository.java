package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarRentInfo;
import org.springframework.data.jpa.repository.*;

public interface CarRentInfoRepository extends JpaRepository<CarRentInfo, Long> {


}
