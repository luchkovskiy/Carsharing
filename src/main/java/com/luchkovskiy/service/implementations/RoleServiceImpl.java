package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.repository.RoleRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    private final UserRepository userRepository;

    @Override
    public Role read(Long id) {
        return repository.read(id);
    }

    @Override
    public List<Role> readAll() {
        return repository.readAll();
    }

    @Override
    public Role create(Role object) {
        return repository.create(object);
    }

    @Override
    public Role update(Role object) {
        return repository.update(object);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }
}
