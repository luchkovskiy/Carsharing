package com.luchkovskiy.repository;

import com.luchkovskiy.domain.User;
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
public class UserRepositoryImpl implements UserRepository {

    private final ConnectionManager connectionManager;

    @Override
    public User read(Long id) {
        connectionManager.loadDriver();
        User user;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            user = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return user;
    }

    @Override
    public List<User> readAll() {
        connectionManager.loadDriver();
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public User create(User object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, surname, birthday_date, created, changed, is_active" +
                    "adress, passport_id, driver_id, driving_experience, role_id, rating)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public User update(User object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE users SET name = ?, surname = ?, birthday_date = ?, created = ?, changed = ?, is_active = ?" +
                    " adress = ?, passport_id = ?, driver_id = ?, driving_experience = ?, role_id = ?, rating = ?  WHERE id = ?");
            fillStatement(object, insertStatement);
            insertStatement.setLong(13, object.getId());
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
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE users SET is_active = false WHERE id = ?");
            insertStatement.setLong(1, id);
            insertStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void hardDelete(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM users");
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

    private User parseResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setBirthday_date(resultSet.getDate("birthday_date"));
        user.setCreated(resultSet.getTimestamp("created"));
        user.setChanged(resultSet.getTimestamp("changed"));
        user.setActive(resultSet.getBoolean("is_active"));
        user.setAddress(resultSet.getString("address"));
        user.setPassport_id(resultSet.getString("passport_id"));
        user.setDriver_id(resultSet.getString("driver_id"));
        user.setDriving_experience(resultSet.getFloat("driving_experience"));
        user.setRole_id(resultSet.getInt("role_id"));
        user.setRating(resultSet.getFloat("rating"));
        return user;
    }

    private void fillStatement(User object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getSurname());
        statement.setDate(3, object.getBirthday_date());
        statement.setTimestamp(4, object.getCreated());
        statement.setTimestamp(5, object.getChanged());
        statement.setBoolean(6, object.getActive());
        statement.setString(7, object.getAddress());
        statement.setString(8, object.getPassport_id());
        statement.setString(9, object.getDriver_id());
        statement.setFloat(10, object.getDriving_experience());
        statement.setInt(11, object.getRole_id());
        statement.setFloat(12, object.getRating());
    }

}
