package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Session;
import com.luchkovskiy.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SessionRepositoryImpl implements SessionRepository {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CarRepository carRepository;
    private final ConnectionManager connectionManager;

    @Override
    public Session read(Long id) {
        connectionManager.loadDriver();
        Session session;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sessions WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            session = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return session;
    }

    @Override
    public List<Session> readAll() {
        connectionManager.loadDriver();
        List<Session> sessions = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM sessions");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sessions.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return sessions;
    }

    @Override
    public Session create(Session object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sessions (user_id, car_id, start_time," +
                    " end_time, total_price, status, km_passed)" +
                    " VALUES (?,?,?,?,?,?,?) RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public Session update(Session object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE sessions SET user_id = ?, car_id = ?, start_time = ?,\" +\n" +
                    "                    \" end_time = ?, total_price = ?, status = ?, km_passed= ? WHERE id = ?");
            fillStatement(object, insertStatement);
            insertStatement.setLong(8, object.getId());
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM sessions WHERE id = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM sessions");
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

    private Session parseResultSet(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setId(resultSet.getLong("id"));
        session.setUser(userRepository.read(resultSet.getLong("id")));
        session.setCar(carRepository.read(resultSet.getLong("id")));
        session.setStartTime(resultSet.getTimestamp("start_time"));
        session.setEndTime(resultSet.getTimestamp("end_time"));
        session.setTotalPrice(resultSet.getFloat("total_price"));
        session.setStatus(resultSet.getString("status"));
        session.setDistancePassed(resultSet.getFloat("distance_passed"));
        return session;
    }

    private void fillStatement(Session object, PreparedStatement statement) throws SQLException {
        statement.setLong(1, object.getUser().getId());
        statement.setLong(2, object.getCar().getId());
        statement.setTimestamp(3, object.getStartTime());
        statement.setTimestamp(4, object.getEndTime());
        statement.setFloat(5, object.getTotalPrice());
        statement.setString(6, object.getStatus());
        statement.setFloat(7, object.getDistancePassed());
    }
}
