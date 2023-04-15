package com.luchkovskiy.service;


import com.luchkovskiy.models.Role;

import java.util.List;

public interface RoleService extends CRUDService<Long, Role> {

    List<Role> getUserAuthorities(Long userId);

}
