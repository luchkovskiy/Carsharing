package com.luchkovskiy.repository;

import com.luchkovskiy.domain.Subscription;
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
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    @Autowired
    UserRepository userRepository;
    private final ConnectionManager connectionManager;

    @Override
    public Subscription read(Long id) {
        connectionManager.loadDriver();
        Subscription subscription;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscriptions WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            subscription = parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return subscription;
    }

    @Override
    public List<Subscription> readAll() {
        connectionManager.loadDriver();
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscriptions");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscriptions.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return subscriptions;
    }

    @Override
    public Subscription create(Subscription object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subscriptions (user_id, start_time, end_time," +
                    "acccess_level, price_per_day, status, amount_of_trips, days_total, created, changed)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,? RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public Subscription update(Subscription object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE subscriptions SET user_id = ?, start_time = ?, end_time = ?,\" +\n" +
                    "                    \"acccess_level = ?, price_per_day = ?, status = ?, amount_of_trips = ?, days_total = ?, created = ?, changed = ? WHERE id = ?");
            fillStatement(object, insertStatement);
            insertStatement.setLong(11, object.getId());
            insertStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return object;
    }

    @Override
    public boolean checkIdValid(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM subscriptions");
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
    public void delete(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM subscriptions WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Subscription parseResultSet(ResultSet resultSet) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setId(resultSet.getLong("id"));
        subscription.setUser(userRepository.read(resultSet.getLong("id")));
        subscription.setStart_time(resultSet.getTimestamp("start_time"));
        subscription.setEnd_time(resultSet.getTimestamp("end_time"));
        subscription.setAccess_level(resultSet.getInt("access_level"));
        subscription.setDay_price(resultSet.getFloat("price_per_day"));
        subscription.setStatus(resultSet.getString("status"));
        subscription.setTrips_amount(resultSet.getInt("amount_of_trips"));
        subscription.setDays_total(resultSet.getInt("days_total"));
        subscription.setCreated(resultSet.getTimestamp("created"));
        subscription.setChanged(resultSet.getTimestamp("changed"));
        return subscription;
    }

    private void fillStatement(Subscription object, PreparedStatement statement) throws SQLException {
        statement.setLong(1, object.getUser().getId());
        statement.setTimestamp(2, object.getStart_time());
        statement.setTimestamp(3, object.getEnd_time());
        statement.setInt(4, object.getAccess_level());
        statement.setFloat(5, object.getDay_price());
        statement.setString(6, object.getStatus());
        statement.setInt(7, object.getTrips_amount());
        statement.setInt(8, object.getDays_total());
        statement.setTimestamp(9, object.getCreated());
        statement.setTimestamp(10, object.getChanged());
    }
}
