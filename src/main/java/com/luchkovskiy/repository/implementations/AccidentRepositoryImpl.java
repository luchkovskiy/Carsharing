package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.repository.AccidentRepository;
import com.luchkovskiy.repository.implementations.rowmappers.AccidentRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@Primary
@RequiredArgsConstructor
public class AccidentRepositoryImpl implements AccidentRepository {

    private final NamedParameterJdbcTemplate template;
    private final AccidentRowMapper accidentRowMapper;

    @Override
    public Accident read(Long id) {
        return template.queryForObject("SELECT * FROM accidents WHERE id = :id", new MapSqlParameterSource("id", id), accidentRowMapper);
    }

    @Override
    public List<Accident> readAll() {
        return template.query("SELECT * FROM accidents", accidentRowMapper);
    }

    @Override
    public Accident create(Accident accident) {
        accident.setId(template.queryForObject("INSERT INTO accidents (session_id, name, fine, time, rating_subtraction, damage_level, is_critical, changed)" +
                        " VALUES (:session_id, :name, :fine, :time, :rating_subtraction, :damage_level, :is_critical, :changed) RETURNING id",
                getParameterSource(accident), Long.class));
        return accident;
    }

    @Override
    public Accident update(Accident accident) {
        template.update("UPDATE accidents SET session_id = :session_id, name = :name, fine = :fine, time = :time, rating_subtraction = :rating_subtraction" +
                "damage_level = :damage_level, is_critical = :is_critical WHERE id = :id", getParameterSource(accident));
        return accident;
    }

    @Override
    public void delete(Long id) {
        template.update("DELETE FROM accidents WHERE id = :id", new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean checkIdValid(Long id) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM accidents WHERE id = :id", new MapSqlParameterSource("id", id), Integer.class);
        return count > 1;
    }

    @Override
    public List<Accident> getAccidentsBySession(Long sessionId) {
        return template.query("SELECT id FROM accidents WHERE session_id = :session_id", new MapSqlParameterSource("session_id", sessionId), accidentRowMapper);
    }

    @Override
    public Integer countAccidents(Long sessionId) {
        SimpleJdbcCall jdbcCall = new
                SimpleJdbcCall(template.getJdbcTemplate()).withProcedureName("countSessionAccidents");
        Map<String, Object> session = jdbcCall.execute(new MapSqlParameterSource("session", sessionId));
        return (Integer) session.get("sessionCount");
    }

    private SqlParameterSource getParameterSource(Accident accident) {
        return new MapSqlParameterSource()
                .addValue("id", accident.getId())
                .addValue("session_id", accident.getSession().getId())
                .addValue("name", accident.getName())
                .addValue("fine", accident.getFine())
                .addValue("time", accident.getTime())
                .addValue("rating_subtraction", accident.getRatingSubtraction())
                .addValue("damage_level", accident.getDamageLevel())
                .addValue("is_critical", accident.getCritical())
                .addValue("changed", Timestamp.valueOf(LocalDateTime.now()));
    }
}
