package com.luchkovskiy.repository;

import com.luchkovskiy.models.SubscriptionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionLevelRepository extends JpaRepository<SubscriptionLevel, Long> {

    @Query("DELETE FROM SubscriptionLevel s WHERE s.id = :id")
    @Modifying
    void deleteSubscriptionLevel(Long id);

}
