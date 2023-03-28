package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    private final UserRepository userRepository;

    @Override
    public Subscription mapRow(ResultSet resultSet, int rowNum) {
        Subscription subscription;
        try {
            subscription = Subscription.builder()
                    .id(resultSet.getLong("id"))
                    .user(userRepository.read(resultSet.getLong("id")))
                    .startTime(resultSet.getTimestamp("start_time"))
                    .endTime(resultSet.getTimestamp("end_time"))
                    .status(resultSet.getString("status"))
                    .tripsAmount(resultSet.getInt("amount_of_trips"))
                    .daysTotal(resultSet.getInt("days_total"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .levelId(resultSet.getInt("level_id"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subscription;
    }

}
