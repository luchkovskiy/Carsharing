package com.luchkovskiy.controllers.converters.base;

import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import com.luchkovskiy.models.Session;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public abstract class SessionBaseConverter<S, T> implements Converter<S, T> {

    public Session doConvert(Session sessionForUpdate, SessionCreateRequest request) {

        sessionForUpdate.setStartTime(request.getStartTime());
        sessionForUpdate.setEndTime(request.getEndTime());
        sessionForUpdate.setStatus(request.getStatus());
        sessionForUpdate.setDistancePassed(request.getDistancePassed());
        sessionForUpdate.setTotalPrice(request.getTotalPrice());
        sessionForUpdate.setChanged(LocalDateTime.now());

        return sessionForUpdate;
    }


}
