package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Session;
import com.luchkovskiy.repository.SessionRepository;
import com.luchkovskiy.repository.implementations.rowmappers.SessionRowMapper;
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
public class SessionRepositoryImpl implements SessionRepository {

    private final NamedParameterJdbcTemplate template;
    private final SessionRowMapper sessionRowMapper;

    @Override
    public Session read(Long id) {
        return template.queryForObject("SELECT * FROM sessions WHERE id = :id", new MapSqlParameterSource("id", id), sessionRowMapper);
    }

    @Override
    public List<Session> readAll() {
        return template.query("SELECT * FROM sessions", sessionRowMapper);
    }

    @Override
    public Session create(Session session) {
        session.setId(template.queryForObject("INSERT INTO sessions (user_id, car_id, start_time, end_time, total_price, status, distance_passed, changed)" +
                        " VALUES (:user_id, :car_id, :start_time, :end_time, :total_price, :status, :distance_passed, :changed) RETURNING id",
                getParameterSource(session), Long.class));
        return session;
    }

    @Override
    public Session update(Session session) {
        template.update("UPDATE sessions SET user_id = :user_id, car_id = :car_id, start_time = :start_time, end_time = :end_time, total_price = :total_price," +
                " status = :status, distance_passed = :distance_passed, changed = :changed" +
                " WHERE id = :id", getParameterSource(session));
        return session;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM sessions WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean checkIdValid(Long id) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM sessions WHERE id = :id", new MapSqlParameterSource("id", id), Integer.class);
        return count > 1;
    }

    private SqlParameterSource getParameterSource(Session session) {
        return new MapSqlParameterSource()
                .addValue("id", session.getId())
                .addValue("user_id", session.getUser().getId())
                .addValue("car_id", session.getCar().getId())
                .addValue("start_time", session.getStartTime())
                .addValue("end_time", session.getEndTime())
                .addValue("total_price", session.getTotalPrice())
                .addValue("status", session.getStatus())
                .addValue("distance_passed", session.getDistancePassed())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()));
    }
}
