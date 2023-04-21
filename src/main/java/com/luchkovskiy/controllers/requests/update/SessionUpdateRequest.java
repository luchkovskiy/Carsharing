package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.SessionCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SessionUpdateRequest extends SessionCreateRequest {

    private Long id;

}
