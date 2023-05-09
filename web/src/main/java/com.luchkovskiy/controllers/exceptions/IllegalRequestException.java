package com.luchkovskiy.controllers.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;

@Getter
@Setter
public class IllegalRequestException extends RuntimeException {

    private BindingResult bindingResult;

    public IllegalRequestException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

}
