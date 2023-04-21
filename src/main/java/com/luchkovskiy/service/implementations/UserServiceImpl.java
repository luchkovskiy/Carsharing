package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import com.luchkovskiy.service.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User object) {
        return userRepository.save(object);
    }

    @Override
    public User update(User object) {
        if (!userRepository.existsById(object.getId()))
            throw new RuntimeException();
        return userRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException();
        userRepository.deleteById(id);
    }

    @Override
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByAuthenticationInfoEmail(email);
    }
}
