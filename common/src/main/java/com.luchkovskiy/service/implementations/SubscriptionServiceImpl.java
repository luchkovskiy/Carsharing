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
        return subscriptionRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Override
    public List<Subscription> readAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription create(Subscription object) {
        return subscriptionRepository.save(object);
    }

    @Override
    public Subscription update(Subscription object) {
        if (!subscriptionRepository.existsById(object.getId()))
            throw new RuntimeException();
        return subscriptionRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!subscriptionRepository.existsById(id))
            throw new RuntimeException();
        subscriptionRepository.deleteById(id);
    }

}
