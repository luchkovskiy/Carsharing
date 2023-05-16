package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.SessionBaseConverter;
import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SessionCreateConverter extends SessionBaseConverter<SessionCreateRequest, Session> {

    private final UserService userService;

    private final CarService carService;

    private final CarRentInfoService carRentInfoService;

    @Override
    public Session convert(SessionCreateRequest request) {

        Session session = new Session();

        session.setCreated(LocalDateTime.now());
        session.setUser(userService.read(request.getUserId()));
        session.setCar(carService.read(request.getCarId()));
        session.setStartLocation(carRentInfoService.readByCarId(request.getCarId()).getCurrentLocation());

        return doConvert(session, request);
    }

}
