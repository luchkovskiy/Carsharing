package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.SubscriptionLevel;
import com.luchkovskiy.repository.SubscriptionLevelRepository;
import com.luchkovskiy.service.SubscriptionLevelService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionLevelServiceImpl implements SubscriptionLevelService {

    private final SubscriptionLevelRepository subscriptionLevelRepository;

    @Cacheable("subscriptionLevels")
    @Override
    public SubscriptionLevel findById(Long id) {
        return subscriptionLevelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subscription level not found!"));
    }

    @Cacheable("subscriptionLevels")
    @Override
    public List<SubscriptionLevel> findAll() {
        return subscriptionLevelRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public SubscriptionLevel create(SubscriptionLevel object) {
        return subscriptionLevelRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public SubscriptionLevel update(SubscriptionLevel object) {
        if (!subscriptionLevelRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Subscription level not found!");
        return subscriptionLevelRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!subscriptionLevelRepository.existsById(id))
            throw new EntityNotFoundException("Subscription level not found!");
        subscriptionLevelRepository.deleteSubscriptionLevel(id);
    }
}
