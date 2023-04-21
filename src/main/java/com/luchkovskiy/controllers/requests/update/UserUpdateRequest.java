package com.luchkovskiy.controllers.requests.update;

import com.luchkovskiy.controllers.requests.create.UserCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest extends UserCreateRequest {

    private Long id;

}
