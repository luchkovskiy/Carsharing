package com.luchkovskiy.security.provider;

import com.luchkovskiy.models.AuthenticationInfo;
import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.SystemRole;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<User> searchResult = userService.findByEmail(email);
            if (searchResult.isPresent()) {
                User user = searchResult.get();
                AuthenticationInfo authenticationInfo = user.getAuthenticationInfo();
                return new org.springframework.security.core.userdetails.User(
                        authenticationInfo.getEmail(),
                        authenticationInfo.getPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                        AuthorityUtils.commaSeparatedStringToAuthorityList(
                                userService.getUserAuthorities(user.getId())
                                        .stream()
                                        .map(Role::getSystemRole)
                                        .map(SystemRole::name)
                                        .collect(Collectors.joining(","))
                        )
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}
