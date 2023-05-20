package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.RoleCreateRequest;
import com.luchkovskiy.models.Role;
import org.springframework.core.convert.converter.Converter;

public abstract class RoleBaseConverter<S, T> implements Converter<S, T> {

    public Role doConvert(Role roleForUpdate, RoleCreateRequest request) {

        roleForUpdate.setSystemRole(request.getSystemRole());

        return roleForUpdate;
    }
}
