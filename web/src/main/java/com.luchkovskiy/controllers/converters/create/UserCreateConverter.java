package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.UserBaseConverter;
import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.models.AuthenticationInfo;
import com.luchkovskiy.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserCreateConverter extends UserBaseConverter<UserCreateRequest, User> {

    @Override
    public User convert(UserCreateRequest request) {

        User user = new User();
        user.setCreated(LocalDateTime.now());
        AuthenticationInfo info = new AuthenticationInfo(request.getEmail(), request.getPassword());
        user.setAuthenticationInfo(info);

        return doConvert(user, request);
    }
}
