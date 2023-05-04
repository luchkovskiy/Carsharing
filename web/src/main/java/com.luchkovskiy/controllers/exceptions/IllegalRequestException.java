package com.luchkovskiy.controllers.exceptions;

import lombok.*;
import org.springframework.validation.*;

@Getter
@Setter
public class IllegalRequestException extends RuntimeException {

    private BindingResult bindingResult;

    public IllegalRequestException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

}
