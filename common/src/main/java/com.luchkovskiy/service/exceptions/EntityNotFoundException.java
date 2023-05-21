package com.luchkovskiy.service.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {

    private String message;

    public EntityNotFoundException(String message) {
        this.message = message;
    }

    public EntityNotFoundException() {

    }

}
