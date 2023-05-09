package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccidentUpdateRequest extends AccidentCreateRequest {

    private Long id;

}
