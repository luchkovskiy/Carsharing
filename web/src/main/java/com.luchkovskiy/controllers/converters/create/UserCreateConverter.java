package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.UserBaseConverter;
import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.models.AuthenticationInfo;
import com.luchkovskiy.models.User;
import com.luchkovskiy.security.config.JWTConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserCreateConverter extends UserBaseConverter<UserCreateRequest, User> {

    private final JWTConfiguration configuration;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserCreateRequest request) {

        User user = new User();
        user.setCreated(LocalDateTime.now());
        AuthenticationInfo info = new AuthenticationInfo();
        info.setEmail(request.getEmail());

        String resultPassword = request.getPassword() + configuration.getServerPasswordSalt();

        String encode = passwordEncoder.encode(resultPassword);
        info.setPassword(encode);

        user.setAuthenticationInfo(info);

        return doConvert(user, request);
    }
}
