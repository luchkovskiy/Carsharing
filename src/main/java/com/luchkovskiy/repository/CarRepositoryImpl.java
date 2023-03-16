package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Car;
import com.luchkovskiy.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Primary
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final ConnectionManager connectionManager;

    @Override
    public Car read(Long id) {
        connectionManager.loadDriver();
        Car car;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cars WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            car = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return car;
    }

    @Override
    public List<Car> readAll() {
        connectionManager.loadDriver();
        List<Car> cars = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cars");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return cars;
    }

    @Override
    public Car create(Car object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO cars (brand, model, created," +
                    " changed, is_available, max_speed, color, current_location, release_year, drive_type, gas_consumption)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public Car update(Car object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE cars SET brand = ?, model = ?, created = ?," +
                    "changed = ?, is_available = ?, max_speed = ?, color = ?, current_location = ?, issue_year = ?, drive_type = ?," +
                    " gas_consumption = ? WHERE id = ?");
            fillStatement(object, insertStatement);
            insertStatement.setLong(12, object.getId());
            insertStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public void delete(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM cars WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkIdValid(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM cars");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long currentId = resultSet.getLong("id");
                if (id.equals(currentId)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return false;
    }

    private Car parseResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getLong("id"));
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setCreated(resultSet.getTimestamp("created"));
        car.setChanged(resultSet.getTimestamp("changed"));
        car.setAvailable(resultSet.getBoolean("is_available"));
        car.setMaxSpeed(resultSet.getFloat("max_speed"));
        car.setColor(resultSet.getString("color"));
        car.setCurrentLocation(resultSet.getString("current_location"));
        car.setReleaseYear(resultSet.getInt("release_year"));
        car.setDriveType(resultSet.getString("drive_type"));
        car.setGasConsumption(resultSet.getFloat("gas_consumption"));
        return car;
    }

    private void fillStatement(Car object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getBrand());
        statement.setString(2, object.getModel());
        statement.setTimestamp(3, object.getCreated());
        statement.setTimestamp(4, object.getChanged());
        statement.setBoolean(5, object.getAvailable());
        statement.setFloat(6, object.getMaxSpeed());
        statement.setString(7, object.getColor());
        statement.setString(8, object.getCurrentLocation());
        statement.setInt(9, object.getReleaseYear());
        statement.setString(10, object.getDriveType());
        statement.setFloat(11, object.getGasConsumption());
    }
}
