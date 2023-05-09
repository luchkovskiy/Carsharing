package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.models.User;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class UserBaseConverter<S, T> implements Converter<S, T> {

    public User doConvert(User userForUpdate, UserCreateRequest request) {

        userForUpdate.setName(request.getName());
        userForUpdate.setSurname(request.getSurname());
        userForUpdate.setBirthdayDate(request.getBirthdayDate());
        userForUpdate.setActive(request.getActive());
        userForUpdate.setPassportId(request.getPassportId());
        userForUpdate.setDriverId(request.getDriverId());
        userForUpdate.setDrivingExperience(request.getDrivingExperience());
        userForUpdate.setRating(request.getRating());
        userForUpdate.setAccountBalance(request.getAccountBalance());
        userForUpdate.setChanged(LocalDateTime.now());

        return userForUpdate;
    }
}
