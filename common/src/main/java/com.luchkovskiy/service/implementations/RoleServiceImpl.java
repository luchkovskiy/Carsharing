package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.enums.SystemRole;
import com.luchkovskiy.repository.RoleRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.RoleService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Cacheable("roles")
    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found!"));
    }

    @Cacheable("roles")
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Role create(Role object) {
        if (object.getSystemRole().equals(SystemRole.ROLE_ADMIN)) {
            createModeratorRole(object);
        }
        return roleRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public Role update(Role object) {
        if (!roleRepository.existsById(object.getId()))
            throw new EntityNotFoundException("Role not found!");
        return roleRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!roleRepository.existsById(id))
            throw new EntityNotFoundException("Role not found!");
        basicRoleCheck(id);
        roleRepository.deleteRole(id);
    }

    @Override
    @Cacheable("roles")
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }

    private void createModeratorRole(Role object) {
        Role role = new Role();
        role.setSystemRole(SystemRole.ROLE_MODERATOR);
        role.setUser(object.getUser());
        roleRepository.save(role);
    }

    private void basicRoleCheck(Long id) {
        if (roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found!")).getSystemRole().equals(SystemRole.ROLE_USER)) {
            throw new RuntimeException("Can't delete basic role!");
        }
    }
}
