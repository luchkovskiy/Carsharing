package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Accident;
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
public class AccidentRepositoryImpl implements AccidentRepository {

    @Autowired
    SessionRepository sessionRepository;
    private final ConnectionManager connectionManager;

    @Override
    public Accident read(Long id) {
        connectionManager.loadDriver();
        Accident accident;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accidents WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            accident = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return accident;
    }

    @Override
    public List<Accident> readAll() {
        connectionManager.loadDriver();
        List<Accident> accidents = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accidents");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                accidents.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return accidents;
    }

    @Override
    public Accident create(Accident object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accidents (session_id, name, fine, time, rating_subtraction, damage_level, is_critical)" +
                    "VALUES (?,?,?,?,?,?,?) RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public Accident update(Accident object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE subscriptions SET session_id = ?, name = ?, fine = ?, time = ?, rating_subtraction = ?," +
                    " damage_level = ?, is_critical = ? WHERE id = ?");
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM accidents WHERE id = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM accidents");
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

    @Override
    public List<Accident> getAccidentsBySession(Long sessionId) {
        List<Accident> accidents = new ArrayList<>();
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM accidents WHERE session_id = ?");
            statement.setLong(1, sessionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                accidents.add(read(resultSet.getLong("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return accidents;
    }

    private Accident parseResultSet(ResultSet resultSet) throws SQLException {
        Accident accident = new Accident();
        accident.setId(resultSet.getLong("id"));
        accident.setSession(sessionRepository.read(resultSet.getLong("id")));
        accident.setName(resultSet.getString("name"));
        accident.setFine(resultSet.getFloat("fine"));
        accident.setTime(resultSet.getTimestamp("time"));
        accident.setRating_subtraction(resultSet.getFloat("rating_subtraction"));
        accident.setDamage_level(resultSet.getInt("damage_level"));
        accident.setCritical(resultSet.getBoolean("is_critical"));
        return accident;
    }

    private void fillStatement(Accident object, PreparedStatement statement) throws SQLException {
        statement.setLong(1, object.getSession().getId());
        statement.setString(2, object.getName());
        statement.setFloat(3, object.getFine());
        statement.setTimestamp(4, object.getTime());
        statement.setFloat(5, object.getRating_subtraction());
        statement.setInt(6, object.getDamage_level());
        statement.setBoolean(7, object.getCritical());
    }

}
