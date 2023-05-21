package com.luchkovskiy.util;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import com.luchkovskiy.models.User;
import org.springframework.validation.BindingResult;

import java.security.Principal;

public class ExceptionChecker {

    public static void validCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRequestException(bindingResult);
        }
    }

    public static void authCheck(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorized in the system");
        }
    }

    public static void verifyCheck(User user) {
        if (!user.getActive()) {
            throw new RuntimeException("Please, verify your account to continue!");
        }
    }

}
