package com.luchkovskiy.configuration;

import com.google.maps.GeoApiContext;
import com.luchkovskiy.util.LocationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public LocationManager locationManager(){
        return new LocationManager();
    }

}
