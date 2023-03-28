package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.Car;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet resultSet, int rowNum) {
        Car car;
        try {
            car = Car.builder()
                    .id(resultSet.getLong("id"))
                    .brand(resultSet.getString("brand"))
                    .model(resultSet.getString("model"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .visible(resultSet.getBoolean("is_visible"))
                    .maxSpeed(resultSet.getFloat("max_speed"))
                    .color(resultSet.getString("color"))
                    .releaseYear(resultSet.getInt("release_year"))
                    .gearboxType(resultSet.getString("gearbox_type"))
                    .sitsAmount(resultSet.getInt("amount_of_sits"))
                    .classId(resultSet.getInt("class_id"))
                    .gasConsumption(resultSet.getFloat("gas_consumption"))
                    .licensePlateNumber(resultSet.getString("license_plate_number"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }
}


