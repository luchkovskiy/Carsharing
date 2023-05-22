package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.User;
import com.luchkovskiy.models.enums.SystemRole;
import com.luchkovskiy.repository.RoleRepository;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.service.UserService;
import com.luchkovskiy.service.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public User create(User object) {
        userDrivingInfoCheck(object);
        createBasicRole(object);
        return userRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public User update(User object) {
        if (!userRepository.existsById(object.getId()))
            throw new EntityNotFoundException("User not found!");
        userDrivingInfoCheck(object);
        return userRepository.save(object);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = SQLException.class)
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found!");
        userRepository.inactiveUser(id);
    }

    @Override
    public List<Role> getUserAuthorities(Long userId) {
        return userRepository.getUserAuthorities(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByAuthenticationInfoEmail(email);
    }

    private void createBasicRole(User object) {
        Role role = new Role();
        role.setUser(object);
        role.setSystemRole(SystemRole.ROLE_USER);
        roleRepository.save(role);
    }

    private void userDrivingInfoCheck(User user) {
        if (user.getBirthdayDate().isAfter(LocalDateTime.now().minusYears(18)) || user.getDrivingExperience() < 2) {
            throw new RuntimeException("Your driving information is not valid for using the app");
        }
    }

}
