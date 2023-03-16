package com.luchkovskiy.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:database.properties")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DatabaseProperties {
    @Value("${POSTGRES_DRIVER_NAME}")
    private String driverName;
    @Value("${URL}")
    private String url;
    @Value("${PORT}")
    private String port;
    @Value("${NAME}")
    private String name;
    @Value("${LOGIN}")
    private String login;
    @Value("${PASSWORD}")
    private String password;
    @Value("${JDBC_URL}")
    private String jdbcUrl;
    @Value("${POOL_SIZE}")
    private Integer poolSize;

}
