package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class SessionRowMapper implements RowMapper<Session> {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public Session mapRow(ResultSet resultSet, int rowNum) {
        Session session;
        try {
            session = Session.builder()
                    .id(resultSet.getLong("id"))
                    .user(userRepository.read(resultSet.getLong("id")))
                    .car(carRepository.read(resultSet.getLong("id")))
                    .startTime(resultSet.getTimestamp("start_time"))
                    .endTime(resultSet.getTimestamp("end_time"))
                    .totalPrice(resultSet.getFloat("total_price"))
                    .status(resultSet.getString("status"))
                    .distancePassed(resultSet.getFloat("distance_passed"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return session;
    }

}
