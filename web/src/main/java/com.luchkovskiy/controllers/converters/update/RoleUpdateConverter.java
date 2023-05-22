package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.RoleBaseConverter;
import com.luchkovskiy.controllers.requests.update.RoleUpdateRequest;
import com.luchkovskiy.models.Role;
import com.luchkovskiy.service.RoleService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleUpdateConverter extends RoleBaseConverter<RoleUpdateRequest, Role> {

    private final UserService userService;

    private final RoleService roleService;

    @Override
    public Role convert(RoleUpdateRequest request) {

        Role role = roleService.findById(request.getId());
        role.setUser(userService.findById(request.getUserId()));

        return doConvert(role, request);
    }
}
