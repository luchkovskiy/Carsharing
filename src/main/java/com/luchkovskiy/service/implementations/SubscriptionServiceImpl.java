package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.repository.SubscriptionRepository;
import com.luchkovskiy.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription read(Long id) {
        if (!subscriptionRepository.checkIdValid(id))
            throw new RuntimeException();
        return subscriptionRepository.read(id);
    }

    @Override
    public List<Subscription> readAll() {
        return subscriptionRepository.readAll();
    }

    @Override
    public Subscription create(Subscription object) {
        return subscriptionRepository.create(object);
    }

    @Override
    public Subscription update(Subscription object) {
        if (!subscriptionRepository.checkIdValid(object.getId()))
            throw new RuntimeException();
        return subscriptionRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        if (!subscriptionRepository.checkIdValid(id))
            throw new RuntimeException();
        subscriptionRepository.delete(id);
    }

    @Override
    public boolean checkIdExist(Long id) {
        return subscriptionRepository.checkIdValid(id);
    }

}
