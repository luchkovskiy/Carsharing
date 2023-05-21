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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User object) {
        userDrivingInfoCheck(object);
        createBasicRole(object);
        return userRepository.save(object);
    }

    @Override
    public User update(User object) {
        if (!userRepository.existsById(object.getId()))
            throw new EntityNotFoundException("User not found!");
        userDrivingInfoCheck(object);
        return userRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User not found!");
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

    @Override
    public void linkPaymentCard(Long userId, Long cardId) {
        // TODO: 20.05.2023 принципал
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
