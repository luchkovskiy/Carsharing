package com.luchkovskiy.controllers.converters.create;

import com.luchkovskiy.controllers.converters.base.AccidentBaseConverter;
import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccidentCreateConverter extends AccidentBaseConverter<AccidentCreateRequest, Accident> {

    private final SessionService sessionService;

    @Override
    public Accident convert(AccidentCreateRequest request) {

        Accident accident = new Accident();
        accident.setSession(sessionService.findById(request.getSessionId()));

        return doConvert(accident, request);
    }

}
