package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class CarRentInfoRowMapper implements RowMapper<CarRentInfo> {

    private final CarRepository carRepository;

    @Override
    public CarRentInfo mapRow(ResultSet resultSet, int rowNum) {
        CarRentInfo carRentInfo;
        try {
            carRentInfo = CarRentInfo.builder()
                    .id(resultSet.getLong("id"))
                    .car(carRepository.read(resultSet.getLong("id")))
                    .gasRemaining(resultSet.getFloat("gas_remaining"))
                    .repairing(resultSet.getBoolean("repairing"))
                    .currentLocation(resultSet.getString("current_location"))
                    .condition(resultSet.getFloat("condition"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carRentInfo;
    }
}
