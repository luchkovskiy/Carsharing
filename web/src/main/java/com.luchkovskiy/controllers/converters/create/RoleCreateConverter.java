package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.RoleBaseConverter;
import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import com.luchkovskiy.models.Role;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleCreateConverter extends RoleBaseConverter<RoleCreateRequest, Role> {

    private final UserService userService;

    @Override
    public Role convert(RoleCreateRequest request) {

        Role role = new Role();

        role.setUser(userService.findById(request.getUserId()));

        return doConvert(role, request);
    }

}
