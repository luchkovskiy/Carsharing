package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.SessionBaseConverter;
import com.luchkovskiy.controllers.requests.update.SessionUpdateRequest;
import com.luchkovskiy.models.Session;
import com.luchkovskiy.service.CarRentInfoService;
import com.luchkovskiy.service.CarService;
import com.luchkovskiy.service.SessionService;
import com.luchkovskiy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionUpdateConverter extends SessionBaseConverter<SessionUpdateRequest, Session> {

    private final UserService userService;

    private final CarService carService;

    private final SessionService sessionService;

    private final CarRentInfoService carRentInfoService;

    @Override
    public Session convert(SessionUpdateRequest request) {

        Session session = sessionService.findById(request.getId());
        session.setUser(userService.findById(request.getUserId()));
        session.setCar(carService.findById(request.getCarId()));
        session.setStartLocation(carRentInfoService.findByCarId(request.getCarId()).getCurrentLocation());

        return doConvert(session, request);
    }

}
