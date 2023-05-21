package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.AggregationService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {

    private final UserRepository userRepository;

    @Override
    public boolean deleteInactiveUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new EntityNotFoundException("User not found!");
        User user = userRepository.findById(userId).get();
        if (!user.getActive() && user.getChanged().isBefore(LocalDateTime.now().minusMonths(1))) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
