package com.luchkovskiy.repository;

import com.luchkovskiy.models.CarClassLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CarClassLevelRepository extends JpaRepository<CarClassLevel, Long> {

    @Query("DELETE FROM CarClassLevel c WHERE c.id = :id")
    @Modifying
    void deleteCarClassLevel(Long id);

}
