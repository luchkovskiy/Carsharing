package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.models.SystemRoles;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {


    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) {
        Role role;
        try {
            role = Role.builder()
                    .id(resultSet.getLong("id"))
                    .userId(resultSet.getLong("user_id"))
                    .systemRole(SystemRoles.valueOf(resultSet.getString("role_name")))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return role;
    }
}
