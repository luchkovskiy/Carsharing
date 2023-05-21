package com.luchkovskiy.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
@Setter
@Getter
@ConfigurationProperties("cardconfig")
public class CardConfig {

    private String cvvSalt;

}
