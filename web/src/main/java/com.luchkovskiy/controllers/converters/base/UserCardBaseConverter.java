package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.models.UserCard;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class UserCardBaseConverter<S, T> implements Converter<S, T> {

    public UserCard doConvert(UserCard userCardForUpdate) {

        userCardForUpdate.setChanged(LocalDateTime.now());

        return userCardForUpdate;
    }
}
