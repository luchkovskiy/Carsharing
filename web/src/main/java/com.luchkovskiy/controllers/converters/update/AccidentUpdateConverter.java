package com.luchkovskiy.controllers.converters.update;

import com.luchkovskiy.controllers.converters.base.AccidentBaseConverter;
import com.luchkovskiy.controllers.requests.update.AccidentUpdateRequest;
import com.luchkovskiy.models.Accident;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccidentUpdateConverter extends AccidentBaseConverter<AccidentUpdateRequest, Accident> {

    private final AccidentService accidentService;

    private final SessionService sessionService;

    @Override
    public Accident convert(AccidentUpdateRequest request) {

        Accident accident = accidentService.findById(request.getId());
        accident.setSession(sessionService.findById(request.getSessionId()));

        return doConvert(accident, request);
    }

}
