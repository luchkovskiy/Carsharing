package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.User;
import com.luchkovskiy.repository.UserRepository;
import com.luchkovskiy.repository.implementations.rowmappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;
    private final UserRowMapper userRowMapper;

    @Override
    public User read(Long id) {
        return template.queryForObject("SELECT * FROM users WHERE id = :id", new MapSqlParameterSource("id", id), userRowMapper);
    }

    @Override
    public List<User> readAll() {
        return template.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public User create(User user) {
        user.setId(template.queryForObject("INSERT INTO users (name, surname, birthday_date, changed, is_active, address, passport_id," +
                        " driver_id, driving_experience, role_id, rating, account_balance) VALUES (:name, :surname, :birthday_date, :changed," +
                        " :is_active, :address, :passport_id, :driver_id, :driving_experience, :role_id, :rating, :account_balance) RETURNING id",
                getParameterSource(user), Long.class));
        return user;
    }

    @Override
    public User update(User user) {
        template.update("UPDATE users SET name = :name, surname = :surname, birthday_date = :birthday_date, changed = :changed," +
                " is_active = :is_active, address = :adress, passport_id = :passport_id, driver_id = :driver_id, driving_experience = :driving_experience," +
                " role_id = :role_id, rating = :rating, account_balance = :account_balance WHERE id = :id", getParameterSource(user));
        return user;
    }

    @Override
    public void delete(Long id) {
        template.update("UPDATE users SET is_active = false WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public void hardDelete(Long id) {
        template.update("DELETE FROM users WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean checkIdValid(Long id) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM users WHERE id = :id", new MapSqlParameterSource("id", id), Integer.class);
        return count > 1;
    }

    private SqlParameterSource getParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("surname", user.getSurname())
                .addValue("birthday_date", user.getBirthdayDate())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("is_active", user.getActive())
                .addValue("address", user.getAddress())
                .addValue("passport_id", user.getPassportId())
                .addValue("driver_id", user.getDriverId())
                .addValue("driving_experience", user.getDrivingExperience())
                .addValue("role_id", user.getRoleId())
                .addValue("rating", user.getRating())
                .addValue("account_balance", user.getAccountBalance());

    }

}
