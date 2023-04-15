package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

    @Override
    public List<Role> getUserAuthorities(Long userId) {
       return userRepository.getUserAuthorities(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
