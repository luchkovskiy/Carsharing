package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.repository.RoleRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Cacheable("roles")
    @Override
    public Role read(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

    @Cacheable("roles")
    @Override
    public List<Role> readAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(Role object) {
        return roleRepository.save(object);
    }

    @Override
    public Role update(Role object) {
        if (!roleRepository.existsById(object.getId()))
            throw new RuntimeException();
        return roleRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!roleRepository.existsById(id))
            throw new RuntimeException();
        roleRepository.deleteById(id);
    }

    @Override
    @Cacheable("roles")
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }
}
