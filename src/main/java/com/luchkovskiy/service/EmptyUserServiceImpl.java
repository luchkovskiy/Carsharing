package com.luchkovskiy.service;

import com.luchkovskiy.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmptyUserServiceImpl implements UserService {

    @Override
    public User read(Long id) {
        return null;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User create(User object) {
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean checkIdExist(Long id) {
        return false;
    }
}
