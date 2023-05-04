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
        if (!userRepository.checkIdValid(userId))
            throw new RuntimeException();
        User dbUser = userRepository.read(userId);
        if (!dbUser.getActive()) {
            userRepository.hardDelete(dbUser.getId());
            return true;
        }
        return false;
    }
}
