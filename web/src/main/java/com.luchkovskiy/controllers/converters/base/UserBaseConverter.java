package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import com.luchkovskiy.models.AuthenticationInfo;
import com.luchkovskiy.models.User;
import org.springframework.core.convert.converter.Converter;

public abstract class UserBaseConverter<S, T> implements Converter<S, T> {

    public User doConvert(User userForUpdate, UserCreateRequest request) {

        userForUpdate.setName(request.getName());
        userForUpdate.setSurname(request.getSurname());
        userForUpdate.setBirthdayDate(request.getBirthdayDate());
        userForUpdate.setAddress(request.getAddress());
        userForUpdate.setPassportId(request.getPassportId());
        userForUpdate.setDriverId(request.getDriverId());
        userForUpdate.setDrivingExperience(request.getDrivingExperience());
        userForUpdate.setAccountBalance(request.getAccountBalance());
        return userForUpdate;
    }
}
