package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.repository.SubscriptionRepository;
import com.luchkovskiy.service.SubscriptionService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription read(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription not found!"));
    }

    @Override
    public List<Subscription> readAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription create(Subscription object) {
        activeSubscriptionsCheck(object);
        Float pricePerDay = object.getSubscriptionLevel().getPricePerDay();
        User user = object.getUser();
        balanceCheck(object, pricePerDay, user);
        user.setAccountBalance(user.getAccountBalance() - calculateSubscriptionPrice(pricePerDay, object.getDaysTotal()));
        return subscriptionRepository.save(object);
    }

    @Override
    public Subscription update(Subscription object) {
        if (!subscriptionRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Subscription not found!");
        return subscriptionRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id))
            throw new EntityNotFoundException("Subscription not found!");
        subscriptionRepository.deleteById(id);
    }

    private float calculateSubscriptionPrice(Float pricePerDay, Integer daysTotal) {
        return pricePerDay * daysTotal;
    }

    private void balanceCheck(Subscription object, Float pricePerDay, User user) {
        if (user.getAccountBalance() - calculateSubscriptionPrice(pricePerDay, object.getDaysTotal()) < 0) {
            throw new RuntimeException("Not enough money on account balance");
        }
    }

    private void activeSubscriptionsCheck(Subscription object) {
        Set<Subscription> subscriptions = object.getUser().getSubscriptions();
        for (Subscription subscription : subscriptions) {
            if (subscription.getStatus().equals(StatusType.ACTIVE)) {
                throw new RuntimeException("There can be only one active subscription in the account");
            }
        }
    }
}
