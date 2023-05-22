package com.luchkovskiy.util;

import com.luchkovskiy.models.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

@RequiredArgsConstructor
public class EmailManager {

    private final MessageSource messageSource;

    private final JavaMailSender mailSender;

    public String sendCode(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = generateCode();
        message.setTo(user.getAuthenticationInfo().getEmail());
        message.setSubject(messageSource.getMessage("registration.title", null, Locale.getDefault()));
        message.setText(messageSource.getMessage("registration.message", new Object[]
                {user.getName(), user.getSurname(), code}, Locale.getDefault()));
        mailSender.send(message);
        return code;
    }

    /**
     * This method returns a randomly generated five-digit code consisting of numbers and uppercase letters.
     */
    private String generateCode() {
        return RandomStringUtils.random(5, true, true).toUpperCase();
    }

}
