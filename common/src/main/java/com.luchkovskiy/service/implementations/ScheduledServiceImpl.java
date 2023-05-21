package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.repository.SubscriptionRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteInactiveUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.getActive() && user.getChanged().isBefore(LocalDateTime.now().minusMonths(1))) {
                userRepository.delete(user);
            }
        }
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void inactiveSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription subscription : subscriptions) {
            if (subscription.getEndTime().isBefore(LocalDateTime.now())) {
                subscription.setStatus(StatusType.FINISHED);
            }
        }
    }
}
