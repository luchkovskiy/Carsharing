package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.CarRentInfo;
import com.luchkovskiy.repository.CarRentInfoRepository;
import com.luchkovskiy.repository.implementations.rowmappers.CarRentInfoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarRentInfoRepositoryImpl implements CarRentInfoRepository {

    private final NamedParameterJdbcTemplate template;
    private final CarRentInfoRowMapper carRentInfoRowMapper;

    @Override
    public CarRentInfo read(Long id) {
        return template.queryForObject("SELECT * FROM cars_rent_info WHERE id = :id", new MapSqlParameterSource("id", id), carRentInfoRowMapper);
    }

    @Override
    public List<CarRentInfo> readAll() {
        return template.query("SELECT * FROM cars_rent_info", carRentInfoRowMapper);
    }

    @Override
    public CarRentInfo create(CarRentInfo carRentInfo) {
        carRentInfo.setId(template.queryForObject("INSERT INTO cars_rent_info (car_id, gas_remaining, is_repairing, current_location, is_available, condition)" +
                        " VALUES (:car_id, :gas_remaining, :is_repairing, :current_location, :is_available, :condition) RETURNING id",
                getParameterSource(carRentInfo), Long.class));
        return carRentInfo;
    }

    @Override
    public CarRentInfo update(CarRentInfo carRentInfo) {
        template.update("UPDATE cars_rent_info SET car_id = :car_id, gas_remaining = :gas_remaining, is_repairing = :is_repairing, current_location = :current_location," +
                " is_available = :is_available, condition = :condition WHERE id = :id", getParameterSource(carRentInfo));
        return carRentInfo;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM cars_rent_info WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    private SqlParameterSource getParameterSource(CarRentInfo carRentInfo) {
        return new MapSqlParameterSource()
                .addValue("id", carRentInfo.getId())
                .addValue("car_id", carRentInfo.getCar().getId())
                .addValue("gas_remaining", carRentInfo.getGasRemaining())
                .addValue("is_repairing", carRentInfo.getRepairing())
                .addValue("current_location", carRentInfo.getCurrentLocation())
                .addValue("is_available", carRentInfo.getAvailable())
                .addValue("condition", carRentInfo.getCondition());

    }
}
