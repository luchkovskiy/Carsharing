package com.luchkovskiy.configuration;

import com.luchkovskiy.util.EmailManager;
import com.luchkovskiy.util.LocationManager;
import com.luchkovskiy.util.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Value("${emailconfig.smtpPassword}")
    private String smtpPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public LocationManager locationManager() {
        return new LocationManager();
    }

    @Bean
    public SpringSecurityUtils springSecurityUtils() {
        return new SpringSecurityUtils();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername("carsharingjd2@gmail.com");
        mailSender.setPassword(smtpPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return mailSender;
    }

    @Bean
    public EmailManager emailManager() {
        return new EmailManager(javaMailSender());
    }

    // TODO: 19.05.2023 Разобраться с делитами

}
