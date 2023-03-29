package com.luchkovskiy.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.luchkovskiy")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfig {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate (DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSource hikariDatasource (DatabaseProperties databaseProperties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(databaseProperties.getLogin());
        hikariDataSource.setPassword(databaseProperties.getPassword());
        hikariDataSource.setDriverClassName(databaseProperties.getDriverName());
        hikariDataSource.setMaximumPoolSize(databaseProperties.getPoolSize());
        hikariDataSource.setJdbcUrl(databaseProperties.getJdbcUrl());
        return hikariDataSource;
    }

}
