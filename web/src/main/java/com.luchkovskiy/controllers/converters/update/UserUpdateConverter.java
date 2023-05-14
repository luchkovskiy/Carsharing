package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.UserBaseConverter;
import com.luchkovskiy.controllers.requests.update.UserUpdateRequest;
import com.luchkovskiy.models.User;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdateConverter extends UserBaseConverter<UserUpdateRequest, User> {

    private final UserService userService;

    @Override
    public User convert(UserUpdateRequest request) {

        User user = userService.read(request.getId());

        return doConvert(user, request);
    }

}

// TODO: 13.05.2023 Разобраться с датами (что нужно класть в Request - строку с регуляркой или LocalDateTime @Pattern(regexp = "^\\d{4}.\\d{2}.\\d{2}$") для юзера birthday