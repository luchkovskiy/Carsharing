package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Role;
import com.luchkovskiy.repository.RoleRepository;
import com.luchkovskiy.repository.implementations.rowmappers.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    @Override
    public Role read(Long id) {
        return null;
    }

    @Override
    public List<Role> readAll() {
        return jdbcTemplate.query("SELECT * FROM roles ORDER BY id DESC", roleRowMapper);
    }

    @Override
    public Role create(Role object) {
        return null;
    }

    @Override
    public Role update(Role object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
