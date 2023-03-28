package com.luchkovskiy.repository.implementations.rowmappers;

import com.luchkovskiy.models.Accident;
import com.luchkovskiy.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AccidentRowMapper implements RowMapper<Accident> {

    private final SessionRepository sessionRepository;

    @Override
    public Accident mapRow(ResultSet resultSet, int rowNum) {
        Accident accident;
        try {
            accident = Accident.builder()
                    .id(resultSet.getLong("id"))
                    .session(sessionRepository.read(resultSet.getLong("id")))
                    .name(resultSet.getString("name"))
                    .fine(resultSet.getFloat("fine"))
                    .time(resultSet.getTimestamp("time"))
                    .ratingSubtraction(resultSet.getFloat("rating_subtraction"))
                    .damageLevel(resultSet.getInt("damage_level"))
                    .critical(resultSet.getBoolean("is_critical"))
                    .created(resultSet.getTimestamp("created"))
                    .changed(resultSet.getTimestamp("changed"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accident;
    }
}
