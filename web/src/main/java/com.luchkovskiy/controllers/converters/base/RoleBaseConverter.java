package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import com.luchkovskiy.models.Role;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class RoleBaseConverter<S, T> implements Converter<S, T> {

    public Role doConvert(Role roleForUpdate, RoleCreateRequest request) {

        roleForUpdate.setSystemRole(request.getSystemRole());
        roleForUpdate.setChanged(LocalDateTime.now());

        return roleForUpdate;
    }
}
