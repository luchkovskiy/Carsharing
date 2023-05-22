package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.UserBaseConverter;
import com.luchkovskiy.controllers.requests.update.UserUpdateRequest;
import com.luchkovskiy.models.AuthenticationInfo;
import com.luchkovskiy.models.User;
import com.luchkovskiy.security.config.JWTConfiguration;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdateConverter extends UserBaseConverter<UserUpdateRequest, User> {

    private final UserService userService;

    private final JWTConfiguration configuration;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserUpdateRequest request) {

        User user = userService.findById(request.getId());

        AuthenticationInfo info = new AuthenticationInfo();
        info.setEmail(request.getEmail());

        String resultPassword = request.getPassword() + configuration.getServerPasswordSalt();

        String encode = passwordEncoder.encode(resultPassword);
        info.setPassword(encode);

        user.setAuthenticationInfo(info);
        user.setActive(request.getActive());
        user.setRating(request.getRating());

        return doConvert(user, request);
    }

}
