package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Car;
import com.luchkovskiy.repository.CarRepository;
import com.luchkovskiy.repository.implementations.rowmappers.CarRowMapper;
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
public class CarRepositoryImpl implements CarRepository {

    private final NamedParameterJdbcTemplate template;
    private final CarRowMapper carRowMapper;

    @Override
    public Car read(Long id) {
        return template.queryForObject("SELECT * FROM cars WHERE id = :id", new MapSqlParameterSource("id", id), carRowMapper);
    }

    @Override
    public List<Car> readAll() {
        return template.query("SELECT * FROM cars", carRowMapper);
    }

    @Override
    public Car create(Car car) {
        car.setId(template.queryForObject("INSERT INTO cars (brand, model, changed, is_visible, max_speed, color, release_year," +
                        " gearbox_type, amount_of_sits, class_id, gas_consuption, license_plate_number)" +
                        " VALUES (:brand, :model, :changed, :is_visible, :max_speed, :color, :release_year,:gearbox_type, :amount_of_sits," +
                        " :class_id, :gas_consuption, :license_plate_number ) RETURNING id",
                getParameterSource(car), Long.class));
        return car;
    }

    @Override
    public Car update(Car car) {
        template.update("UPDATE cars SET brand = :brand, model = :model, changed = :changed, is_visible = :is_visible, max_speed = :max_speed, color = :color," +
                " release_year = :release_year, gearbox_type = :gearbox_type, amount_of_sits = :amount_of_sits, class_id = :class_id, gas_consuption = :gas_consuption," +
                " license_plate_number = :license_plate_number WHERE id = :id", getParameterSource(car));
        return car;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM cars WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean checkIdValid(Long id) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM cars WHERE id = :id", new MapSqlParameterSource("id", id), Integer.class);
        return count > 1;
    }

    private SqlParameterSource getParameterSource(Car car) {
        return new MapSqlParameterSource()
                .addValue("id", car.getId())
                .addValue("brand", car.getBrand())
                .addValue("model", car.getModel())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()))
                .addValue("is_visible", car.getVisible())
                .addValue("max_speed", car.getMaxSpeed())
                .addValue("color", car.getColor())
                .addValue("release_year", car.getReleaseYear())
                .addValue("gearbox_type", car.getGearboxType())
                .addValue("amount_of_sits", car.getSitsAmount())
                .addValue("class_id", car.getClassId())
                .addValue("gas_consumption", car.getGasConsumption())
                .addValue("license_plate_number", car.getLicensePlateNumber());
    }
}
