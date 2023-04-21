package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.*;
import com.luchkovskiy.repository.*;
import com.luchkovskiy.service.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Override
    public Role read(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Info not found!"));
    }

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
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }
}
