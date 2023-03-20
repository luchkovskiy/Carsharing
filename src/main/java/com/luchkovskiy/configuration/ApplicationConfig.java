package com.luchkovskiy.configuration;

import com.luchkovskiy.util.ConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.luchkovskiy")
public class ApplicationConfig {

    @Bean
    public ConnectionManager connectionManager(DatabaseProperties databaseProperties) {
        return new ConnectionManager(databaseProperties);
    }
}
