package com.luchkovskiy.service;

import com.luchkovskiy.domain.User;
import com.luchkovskiy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User read(Long id) {
        if (!userRepository.checkIdValid(id))
            throw new RuntimeException();
        return userRepository.read(id);
    }

    @Override
    public List<User> readAll() {
        return userRepository.readAll();
    }

    @Override
    public User create(User object) {
        return userRepository.create(object);
    }

    @Override
    public User update(User object) {
        if (!userRepository.checkIdValid(object.getId()))
            throw new RuntimeException();
        return userRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.checkIdValid(id))
            throw new RuntimeException();
        userRepository.delete(id);
    }

    @Override
    public boolean checkIdExist(Long id) {
        return userRepository.checkIdValid(id);
    }
}
