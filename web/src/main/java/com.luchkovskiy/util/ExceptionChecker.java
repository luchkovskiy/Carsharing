package com.luchkovskiy.util;

import com.luchkovskiy.controllers.exceptions.IllegalRequestException;
import org.springframework.validation.BindingResult;

public class ExceptionChecker {

    public static void check(BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new IllegalRequestException(bindingResult);
    }

}
