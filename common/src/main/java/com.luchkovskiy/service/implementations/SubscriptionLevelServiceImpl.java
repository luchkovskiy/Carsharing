package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.SubscriptionLevel;
import com.luchkovskiy.repository.SubscriptionLevelRepository;
import com.luchkovskiy.service.SubscriptionLevelService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionLevelServiceImpl implements SubscriptionLevelService {

    private final SubscriptionLevelRepository subscriptionLevelRepository;

    @Cacheable("subscriptionLevels")
    @Override
    public SubscriptionLevel read(Long id) {
        return subscriptionLevelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription level not found!"));
    }

    @Cacheable("subscriptionLevels")
    @Override
    public List<SubscriptionLevel> readAll() {
        return subscriptionLevelRepository.findAll();
    }

    @Override
    public SubscriptionLevel create(SubscriptionLevel object) {
        return subscriptionLevelRepository.save(object);
    }

    @Override
    public SubscriptionLevel update(SubscriptionLevel object) {
        if (!subscriptionLevelRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Subscription level not found!");
        return subscriptionLevelRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!subscriptionLevelRepository.existsById(id))
            throw new EntityNotFoundException("Subscription level not found!");
        subscriptionLevelRepository.deleteById(id);
    }
}
