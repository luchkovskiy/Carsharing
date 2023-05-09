package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.AggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {

    private final UserRepository userRepository;

    @Override
    public boolean deleteInactiveUser(Long userId) {
        if (!userRepository.existsById(userId))
            throw new RuntimeException();
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        if (!user.getActive()) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
