package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.AccidentCreateRequest;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccidentUpdateRequest extends AccidentCreateRequest {

    private Long id;

}
