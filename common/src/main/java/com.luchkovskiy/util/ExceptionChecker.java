package com.luchkovskiy.util;

import com.luchkovskiy.models.User;
import org.springframework.validation.BindingResult;

import java.security.Principal;

public class ExceptionChecker {

    public static void validCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("Validation error");
        }
    }

    public static void authCheck(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorized in the system");
        }
    }

    public static void verifyCheck(User user) {
        if (!user.getActive()) {
            throw new RuntimeException("This account isn't active");
        }
    }

    public static void ableToPayCheck(User user) {
        if (user.getCards().isEmpty()) {
            throw new RuntimeException("Please, select payment method to continue");
        }
    }

    public static void accountBalanceCheck(User user) {
        if (user.getAccountBalance() < 0) {
            throw new RuntimeException("Not enough money on balance to continue");
        }
    }

}
