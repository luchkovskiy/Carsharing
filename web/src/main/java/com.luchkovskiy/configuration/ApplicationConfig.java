package com.luchkovskiy.configuration;

import com.google.maps.GeoApiContext;
import com.luchkovskiy.util.EmailManager;
import com.luchkovskiy.util.LocationManager;
import com.luchkovskiy.util.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Value("${emailconfig.smtpPassword}")
    private String smtpPassword;

    @Value("${emailconfig.host}")
    private String host;

    @Value("${emailconfig.port}")
    private int port;

    @Value("${emailconfig.username}")
    private String username;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Bean
    public LocationManager locationManager(GeoApiContext geoApiContext) {
        return new LocationManager(geoApiContext);
    }

    @Bean
    public SpringSecurityUtils springSecurityUtils() {
        return new SpringSecurityUtils();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(smtpPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");
        return mailSender;
    }

    @Bean
    public EmailManager emailManager() {
        return new EmailManager(messageSource(), javaMailSender());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
