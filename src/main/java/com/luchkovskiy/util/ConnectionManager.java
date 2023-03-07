package com.luchkovskiy.util;

import com.luchkovskiy.configuration.DatabaseProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class ConnectionManager {

    private final DatabaseProperties properties;

    public void loadDriver() {
        try {
            Class.forName(properties.getDriverName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load driver");
        }
    }

    public Connection open() {
        String jdbcURL = StringUtils.join(properties.getUrl(), properties.getPort(), properties.getName());
        try {
            return DriverManager.getConnection(jdbcURL, properties.getLogin(), properties.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to open connection");
        }
    }

}
