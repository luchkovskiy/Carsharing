package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Subscription;
import com.luchkovskiy.repository.SubscriptionRepository;
import com.luchkovskiy.repository.implementations.rowmappers.SubscriptionRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final NamedParameterJdbcTemplate template;
    private final SubscriptionRowMapper subscriptionRowMapper;

    @Override
    public Subscription read(Long id) {
        return template.queryForObject("SELECT * FROM subscriptions WHERE id = :id", new MapSqlParameterSource("id", id), subscriptionRowMapper);
    }

    @Override
    public List<Subscription> readAll() {
        return template.query("SELECT * FROM subscriptions", subscriptionRowMapper);
    }

    @Override
    public Subscription create(Subscription subscription) {
        subscription.setId(template.queryForObject("INSERT INTO subscriptions (user_id, start_time, end_time, status, amount_of_trips, " +
                        "days_total, changed, level_id) VALUES (:user_id, :start_time, :end_time, :status, :amount_of_trips, :days_total, :changed, :level_id ) RETURNING id",
                getParameterSource(subscription), Long.class));
        return subscription;
    }

    @Override
    public Subscription update(Subscription subscription) {
        template.update("UPDATE subscriptions SET user_id = :user_id, start_time = :start_time, end_time = :end_time, status = :status," +
                " amount_of_trips = :amount_of_trips, days_total = :days_total, changed = :changed, level_id = :level_id " +
                "WHERE id = :id", getParameterSource(subscription));
        return subscription;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM subscriptions WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean checkIdValid(Long id) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM subscriptions WHERE id = :id", new MapSqlParameterSource("id", id), Integer.class);
        return count > 1;
    }

    private SqlParameterSource getParameterSource(Subscription subscription) {
        return new MapSqlParameterSource()
                .addValue("id", subscription.getId())
                .addValue("user_id", subscription.getUser().getId())
                .addValue("start_time", subscription.getStartTime())
                .addValue("end_time", subscription.getEndTime())
                .addValue("status", subscription.getStatus())
                .addValue("amount_of_trips", subscription.getTripsAmount())
                .addValue("days_total", subscription.getDaysTotal())
                .addValue("level_id", subscription.getLevelId())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()));
    }
}
