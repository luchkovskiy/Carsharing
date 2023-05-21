package com.luchkovskiy.repository;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUser(User user);

}
