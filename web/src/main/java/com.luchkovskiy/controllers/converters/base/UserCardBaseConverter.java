package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.models.UserCard;
import org.springframework.core.convert.converter.Converter;

public abstract class UserCardBaseConverter<S, T> implements Converter<S, T> {

    public UserCard doConvert(UserCard userCardForUpdate) {

        return userCardForUpdate;
    }
}
