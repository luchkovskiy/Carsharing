package com.luchkovskiy.util;

import com.luchkovskiy.models.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class EmailManager {

    private final JavaMailSender mailSender;

    public String sendCode(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = generateCode();
        message.setTo(user.getAuthenticationInfo().getEmail());
        message.setSubject("Welcome to Carsharing!");
        message.setText("Thank you for registering in our application, " + user.getName() + " " + user.getSurname() +
                ". Please confirm your email by entering the following code: " + code);
        mailSender.send(message);
        return code;
    }

    private String generateCode() {
        return RandomStringUtils.random(5, true, true).toUpperCase();
    }

}
