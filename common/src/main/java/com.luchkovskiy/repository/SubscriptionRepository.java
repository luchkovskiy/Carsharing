package com.luchkovskiy.repository;

import com.luchkovskiy.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("DELETE FROM Subscription s WHERE s.id = :id")
    @Modifying
    void deleteSubscription(Long id);

}
