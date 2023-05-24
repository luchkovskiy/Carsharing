package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.StatusType;
import com.luchkovskiy.repository.SubscriptionLevelRepository;
import com.luchkovskiy.repository.SubscriptionRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.SubscriptionService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    private final SubscriptionLevelRepository subscriptionLevelRepository;

    private final EntityManager entityManager;

    @Override
    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription not found!"));
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Subscription create(Subscription object) {
        validCheck(object);
        activeSubscriptionsCheck(object);
        Float pricePerDay = object.getSubscriptionLevel().getPricePerDay();
        User user = object.getUser();
        balanceCheck(object, pricePerDay, user);
        user.setAccountBalance(user.getAccountBalance() - calculateSubscriptionPrice(pricePerDay, object.getDaysTotal()));
        return subscriptionRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Subscription update(Subscription object) {
        entityManager.clear();
        if (!subscriptionRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Subscription not found!");
        validCheck(object);
        return subscriptionRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id))
            throw new EntityNotFoundException("Subscription not found!");
        subscriptionRepository.deleteSubscription(id);
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

    private void validCheck(Subscription subscription) {
        userCheck(subscription);
        timeCheck(subscription);
        levelCheck(subscription);
    }

    private void userCheck(Subscription subscription) {
        if (!userRepository.existsById(subscription.getUser().getId())) {
            throw new EntityNotFoundException("User not found");
        }
    }

    private void timeCheck(Subscription subscription) {
        if (subscription.getEndTime().isBefore(subscription.getStartTime())) {
            throw new RuntimeException("Start time can't be later than end time");
        }
    }

    private void levelCheck(Subscription subscription) {
        if (!subscriptionLevelRepository.existsById(subscription.getSubscriptionLevel().getId())) {
            throw new EntityNotFoundException("Subscription level not found");
        }
    }
}
