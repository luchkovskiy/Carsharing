package com.luchkovskiy.util;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
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

}
