package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) {
        User user;
        try {
            user = User.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .birthdayDate(resultSet.getDate("birthday_date"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .active(resultSet.getBoolean("is_active"))
                    .address(resultSet.getString("address"))
                    .passportId(resultSet.getString("passport_id"))
                    .driverId(resultSet.getString("driver_id"))
                    .drivingExperience(resultSet.getFloat("driving_experience"))
                    .roleId(resultSet.getInt("role_id"))
                    .rating(resultSet.getFloat("rating"))
                    .accountBalance(resultSet.getFloat("account_balance"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
