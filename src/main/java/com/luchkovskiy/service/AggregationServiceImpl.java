package com.luchkovskiy.service;

import com.luchkovskiy.domain.User;
import com.luchkovskiy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
@RequiredArgsConstructor
public class AggregationServiceImpl implements AggregationService {

    @Autowired
    UserRepository userRepository;

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
